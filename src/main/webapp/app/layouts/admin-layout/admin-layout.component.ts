import { Component } from '@angular/core';
import { SidebarComponent } from 'app/shared/template/componets/sidebar/sidebar.component';
import { HeaderComponent } from 'app/shared/template/componets/header/header.component';
import { DashboardComponent } from 'app/administration/dashboard/dashboard/dashboard.component';
import { FooterComponent } from 'app/shared/template/componets/footer/footer.component';
import { DashboardRoutingModule } from 'app/administration/dashboard/dashboard.routes';
import { RouterModule } from '@angular/router';
declare var $: any;
@Component({
  selector: 'jhi-admin-layout',
  imports: [SidebarComponent, HeaderComponent, FooterComponent, RouterModule],
  templateUrl: './admin-layout.component.html',
  styleUrl: './admin-layout.component.scss',
})
export class AdminLayoutComponent {
  ngOnInit(): void {
    this.initKaiadmin();
  }

  private initKaiadmin(): void {
    setTimeout(() => {
      // ✅ Sidebar toggle → mode icon-only
      $('.toggle-sidebar')
        .off('click')
        .on('click', () => {
          $('body').toggleClass('sidebar-mini');
        });

      // ✅ Sidenav toggler → mobile
      $('.sidenav-toggler')
        .off('click')
        .on('click', () => {
          $('body').toggleClass('sidebar-show');
        });

      // ✅ Topbar toggler → mobile
      $('.topbar-toggler')
        .off('click')
        .on('click', () => {
          $('body').toggleClass('topbar-show');
        });

      // ✅ Scrollbar
      if ($.fn && $.fn.scrollbar) {
        $('.scrollbar-inner').scrollbar();
        $('.scrollbar-outer').scrollbar();
      }

      // ✅ Collapse du menu sidebar (sous-menus)
      $('.nav-item a[data-bs-toggle="collapse"]')
        .off('click')
        .on('click', () => {
          const target = $(this).attr('href');
          $(target).collapse('toggle');
        });
    }, 300);
  }
}
