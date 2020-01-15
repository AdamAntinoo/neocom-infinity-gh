// - CORE
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { addMilliseconds } from 'date-fns';
import { differenceInMilliseconds } from 'date-fns';

export class ActiveCacheWrapper<T> {
    private _loadState: boolean = false;
    private _timedCache: boolean = false;
    private _expirationTime: number = 60 * 5; // Number of seconds data is valid on cache. 5 minutes.
    private _canReturnObsoletes: boolean = true;
    private _subject: BehaviorSubject<T | T[]> = new BehaviorSubject<T | T[]>(null); // Set the initial value
    private _cache = this._subject.asObservable();
    private _lastUpdateTime = new Date();
    private _downloader: any;
    private _locator: any;

    // - C A C H E   M A I N T E N A N C E
    public accessData(): Observable<T | T[]> {
        // Check if the data is ready.
        console.log('-[ActiveCacheWrapper.accessData]> _loadState: ' + this._loadState);
        if (this._loadState) {
            // Check the cache time status.
            let now = new Date();
            let expirationTime = addMilliseconds(this._lastUpdateTime, this._expirationTime * 1000)
            let diff = differenceInMilliseconds(expirationTime, now);
            if (diff > 0)
                return this._cache;
            else {
                // Cache expired. Decide if we wait or return an obsolete response.
                if (this._canReturnObsoletes) {
                    this.fireDownloader();
                    return this._cache;
                } else return this.fireDownloader();
            }
        } else return this.fireDownloader();
    }
    public storeData(_newdata: T | T[]): void {
        this._loadState = true;
        this._lastUpdateTime = new Date();
        this._subject.next(_newdata);
    }
    public clear(): void {
        this._subject.next(null);
        this._loadState = false;
    }
    public accessLastData(): T | T[] {
        return this._subject.value;
    }

    private fireDownloader(): Observable<T | T[]> {
        console.log('>[ActiveCacheWrapper.fireDownloader]');
        // Start a new download and return the new observable to the caller.
        return this._downloader()
            .pipe(map((data: T | T[]) => {
                // Store into the subject the new data. But only if not null.
                console.log('>[ActiveCacheWrapper.fireDownloader]> data: ' + JSON.stringify(data));
                if (null == data) {
                    this._subject.next(data);
                    this._loadState = false;
                    return data;
                } else {
                    this.storeData(data);
                    return data;
                }
            }));
    }

    // - G E T T E R S   &   S E T T E R S
    public setTimedCache(_newstate: boolean): ActiveCacheWrapper<T> {
        this._timedCache = _newstate;
        return this;
    }
    public setCachingTime(_newtime: number): ActiveCacheWrapper<T> {
        this._expirationTime = _newtime;
        return this;
    }
    public setReturnObsoletes(_newstate: boolean): ActiveCacheWrapper<T> {
        this._canReturnObsoletes = _newstate;
        return this;
    }
    public setDownloader(_downloadCall: any): ActiveCacheWrapper<T> {
        this._downloader = _downloadCall;
        return this;
    }
    public setLocator(_downloadLocator: any): ActiveCacheWrapper<T> {
        this._locator = _downloadLocator;
        return this;
    }
}
