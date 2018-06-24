import { Pipe } from '@angular/core';
import { PipeTransform } from '@angular/core';
import { DecimalPipe } from '@angular/common';

@Pipe({
  name: 'iskscaled'
})
export class IskScaledPipe implements PipeTransform {
  transform(value: any, decimals: number): string {
    if (value == null) return "0.0 ISK";
    let optimizedValue = 0.0;
    let scale = " ISK";
    if (value > 2000000) {
      optimizedValue = value / 1000.0;
      scale = " kISK";
    }
    if (value > 130000000) {
      optimizedValue = value / 1000000.0;
      scale = " MISK";
    }
    let result = new Intl.NumberFormat('en-us', { minimumFractionDigits: decimals })
      .format(Number(optimizedValue)
      );
    return result + scale;
  }
}
