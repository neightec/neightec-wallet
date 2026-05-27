import { Component, Input } from '@angular/core';
import { Store } from '@ngrx/store';
import * as dashboardActions from '../../features/modules/dashboard/store/dashboard.actions';
import * as dashboardSelector from '../../features/modules/dashboard/store/dashboard.selectors';
import { NeightecColumnTableData } from 'src/app/models/neightec-column-table.model';

@Component({
  selector: 'neightec-table',
  templateUrl: './neightec-table.component.html',
  styleUrl: './neightec-table.component.scss',
  standalone: false
})
export class NeightecTableComponent {

  constructor(
    private store: Store
  ) {}

  @Input() 
  columns: NeightecColumnTableData[];

  get displayedColumns(): string[] {
    return this.columns.map(c => c.columnDef);
  }

  dataSource: any;
  loadData$ = this.store.select(dashboardSelector.selectDashboard);

  ngOnInit(): void {
    this.store.dispatch(dashboardActions.loadDataDashboardState());
    this.loadData$.subscribe(res => {
      if (res) {
        this.dataSource = res;
      }
    });
  }
}