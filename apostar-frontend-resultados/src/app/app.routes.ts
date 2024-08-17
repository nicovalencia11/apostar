import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ConfigFormComponent } from './config-form/config-form.component';

export const routes: Routes = [
    {
        path:'',
        component:HomeComponent,
        title:'Pagina de inicio'
    },
    {
        path:'config-form/:code',
        component:ConfigFormComponent,
        title:'formulario de configuracion'
    },
    {
        path:'**',
        redirectTo:'',
        pathMatch:'full' 
    }
];
