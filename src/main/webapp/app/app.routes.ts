import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';

const routes: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: 'admin',
        loadChildren: () => import('./administration/administration.routes').then(m => m.ADMINISTRATION_ROUTE),
      },
    ],
  },
];

export default routes;
