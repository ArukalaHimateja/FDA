import { formatDate } from "@angular/common";
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
    name: "dateTime"
})
export class FormatDateTimePipe implements PipeTransform {
    transform(value: Date): string {
        return value ? formatDate(value, 'MMM d, yyyy, HH:mm', 'en-US', '+0530') : '';
    }
}