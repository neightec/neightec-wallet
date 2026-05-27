import { Component, ViewChild } from '@angular/core';
import { MatDrawer, MatDrawerContainer, MatSidenavModule } from '@angular/material/sidenav';
import { NeightecColumnTableData } from 'src/app/models/neightec-column-table.model';

@Component({
  selector: 'neightec-home',
  templateUrl: './neightec-home.component.html',
  styleUrl: './neightec-home.component.scss',
  standalone: false
})
export class NeightecHomeComponent {

  @ViewChild('drawer') 
  drawer: MatDrawer;

  @ViewChild('drawerContainer') 
  drawerContainer: MatDrawerContainer;
  
  showFiller = false;
  selectedTabIndex: number = 1;

  columnTable: NeightecColumnTableData[] = [
    { columnDef: 'name', header: 'GuestList.Name', key: 'name' },
    { columnDef: 'attendanceStatus', header: 'GuestList.Status', key: 'attendanceStatus' },
    { columnDef: 'date', header: 'GuestList.Date', key: 'date' },
  ];
}
