import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'convertDistance'
})
export class ConvertDistancePipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return parseFloat(value) / 1000;
  }

}
