import { AbstractControl, AsyncValidator, ValidationErrors } from "@angular/forms";
import { catchError, map, Observable, of, switchMap, timer } from "rxjs";
import { Injectable } from '@angular/core';
import { GuestWeddingListService } from "../services/guest-wedding-list.service";

@Injectable({ providedIn: 'root' })
export class GuestFullnameValidator implements AsyncValidator {

  constructor(protected guestWeddingListService: GuestWeddingListService) { }

  validate(control: AbstractControl): Observable<ValidationErrors | null> {
    return timer(250).pipe(
      switchMap(() => this.guestWeddingListService.validateUniqueGuestFullname(control.value)),
      //catchError(() => of(false)),
      map((result: boolean) => (result ? null : { guestFullNameIsNotUnique: true })),
      catchError(() => of({ validationNotPossible: true }))
    );
  }
}
