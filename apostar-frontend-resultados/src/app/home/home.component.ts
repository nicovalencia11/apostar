import { ConfigService } from './../services/config.service';
import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { RouterModule } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Configuration } from '../models/Configuration';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ButtonModule, CardModule, TableModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  configs: Configuration[] = [];
  constructor(
    private configService: ConfigService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.getAllBooks();
  }

  getAllBooks() {
    this.configService.getConfigurations().subscribe((data) => {
      this.configs = data;
    });
  }
}
