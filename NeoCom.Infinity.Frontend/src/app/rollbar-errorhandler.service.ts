// - CORE
import { Injectable } from '@angular/core';
import { ErrorHandler } from '@angular/core';
import { Injector } from '@angular/core';
import { Inject } from '@angular/core';
import { InjectionToken } from '@angular/core';
// - ERROR INTERCEPTION
import * as Rollbar from 'rollbar';
import { AppStoreService } from '@app/services/appstore.service';
import { environment } from '@env/environment.prod';

export const rollbarConfig = {
  accessToken: '9a8dc1d8aa094a9bbe4e4d093faf1e1a',
  captureUncaught: true,
  captureUnhandledRejections: true,
};

@Injectable()
export class RollbarErrorHandler implements ErrorHandler {
  // constructor(@Inject(RollbarService) private rollbar: Rollbar) { }
  constructor(private injector: Injector) { }

  handleError(err: any): void {
    // tslint:disable-next-line: no-use-before-declare
    const rollbar = this.injector.get(RollbarService);
    const appStoreService = this.injector.get(AppStoreService);
    // Use type checking to detect the different types of errors.
    if (err.constructor.name === 'TypeError') {
      // Those are syntax exceptions detected on the runtime.
      console.log('ERR[RollbarErrorHandler.handleError]> Error intercepted: ' + JSON.stringify(err.message));
      if (environment.showexceptions) {
        appStoreService.errorNotification(err.message, 'EXCEPCION ' + err.status)
      }
      rollbar.error(err);
    } else if (err.constructor.name === 'Error') {
      // Those are syntax exceptions detected on the runtime.
      console.log('ERR[RollbarErrorHandler.handleError]> Error intercepted: ' + JSON.stringify(err.message));
       if (environment.showexceptions) {
        appStoreService.errorNotification(err.message, 'EXCEPCION ' + err.status)
      }
      rollbar.error(err);
    } else {
      console.log('ERR[RollbarErrorHandler.handleError]> Error intercepted: ' + JSON.stringify(err.message));
       if (environment.showexceptions) {
        appStoreService.errorNotification(err.message, 'EXCEPCION ' + err.status)
      }
      rollbar.error(err);
    }
  }
}

export function rollbarFactory() {
  return new Rollbar(rollbarConfig);
}
export const RollbarService = new InjectionToken<Rollbar>('rollbar');
