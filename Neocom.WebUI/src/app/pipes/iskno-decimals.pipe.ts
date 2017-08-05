import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'iSKNoDecimals'
})
export class ISKNoDecimalsPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return null;
  }

}
