import { BackendService } from '@app/services/backend.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Angular6BackendService extends BackendService {
  public checkIsolation(): boolean {
    return true;
  }
}