import { Component } from '@angular/core';
import { SidebarComponent } from 'app/shared/template/componets/sidebar/sidebar.component';
import { HeaderComponent } from 'app/shared/template/componets/header/header.component';
import { DashboardComponent } from 'app/administration/dashboard/dashboard/dashboard.component';
import { FooterComponent } from 'app/shared/template/componets/footer/footer.component';

@Component({
  selector: 'jhi-admin-layout',
  imports: [SidebarComponent, HeaderComponent, DashboardComponent, FooterComponent],
  templateUrl: './admin-layout.component.html',
  styleUrl: './admin-layout.component.scss',
})
export class AdminLayoutComponent {}
