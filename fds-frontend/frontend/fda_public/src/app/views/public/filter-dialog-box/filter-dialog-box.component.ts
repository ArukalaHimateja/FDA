import { Component, Inject } from '@angular/core';

import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-filter-dialog-box',
  templateUrl: './filter-dialog-box.component.html',
  styleUrls: ['./filter-dialog-box.component.scss']
})
export class FilterDialogBoxComponent {
  constructor(
    public dialogRef: MatDialogRef<FilterDialogBoxComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) { }
  closeDialog() {
    this.dialogRef.close();
  }
  selectedOption: string = 'sortBy';
  selectOptionforFilterBox(option: string) {
    this.selectedOption = option;
  }

  cuisines = ['Abruzzese','Aceh','Aegean','Afghan','Afghani','African','Agritourism','Alentejana','Algerian','Altoatesine',]

  formatLabel(value: number): string {
    if (value >= 1600) {
      return 'Any';
    }

    return `₹${value}`;
  }

  preCostValue= 0;
  postCostValue=1600;
  preRatingValue=1;
  postRatingValue=5;
}


