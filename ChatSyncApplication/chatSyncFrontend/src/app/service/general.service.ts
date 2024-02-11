import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class GeneralService {

  constructor(private _matSnackBar: MatSnackBar) { }

  openSnackBar(informationText: string, actionText: string) {
    this._matSnackBar.open(informationText, actionText, {
      duration: 2000,
      horizontalPosition: 'right',
      verticalPosition: 'bottom'
    })
  }
}
