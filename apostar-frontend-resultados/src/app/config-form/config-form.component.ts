import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FileSelectEvent, FileUploadModule } from 'primeng/fileupload';
import { ConfigService } from '../services/config.service';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-config-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,
    ButtonModule,
    RouterModule,
    InputTextModule,
    InputNumberModule,
    CardModule,
    FileUploadModule
  ],
  templateUrl: './config-form.component.html',
  styleUrl: './config-form.component.css'
})
export class ConfigFormComponent {

  formConfig!: FormGroup;
  isSaveInProgress: boolean = false;
  edit: boolean = false;
  selectedFile: File | null = null;
  base64String: string = '';

  constructor(
    private fb: FormBuilder,
    private configService: ConfigService,
    private activatedRoute: ActivatedRoute,
    private messageService: MessageService,
    private router: Router
  ) {
    this.formConfig = this.fb.group({
      code: [null],
      time: ['00:00:00', Validators.required],
      emails: [[], Validators.required],
      endPoint: [1, [Validators.required, Validators.min(1)]],
      background: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    let code = this.activatedRoute.snapshot.paramMap.get('code');
    if (code !== 'new') {
      this.edit = true;
      this.getConfigByCode(+code!);
    }
  }

  onFileSelected(event: FileSelectEvent) {
    this.selectedFile = event.files[0];
    const file: File = event.files[0];
  
    if (file && file.type.startsWith('image/')) {
      const reader = new FileReader();
  
      reader.onload = (e: any) => {
        this.base64String = e.target.result.split(',')[1];
        this.formConfig.patchValue({ background: this.base64String });
      };
  
      reader.readAsDataURL(file);
    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Por favor selecciona un archivo de imagen válido.',
      });
    }
  }

  getConfigByCode(code: number) {
    this.configService.getConfigurationByCode(code).subscribe({
      next: (foundConfig) => {
        this.formConfig.patchValue(foundConfig);
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No encontrado',
        });
        this.router.navigateByUrl('/');
      },
    });
  }

  createConfig() {
    if (this.formConfig.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Revise los campos e intente nuevamente',
      });
      return;
    }
    if (!this.selectedFile) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Seleccione una imagen e intente nuevamente',
      });
      return;
    }
    this.isSaveInProgress = true;

    this.configService
      .createConfiguration(this.formConfig.value)
      .subscribe({
        next: () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Guardada',
            detail: 'Configuración guardada correctamente',
          });
          this.isSaveInProgress = false;
          this.router.navigateByUrl('/');
        },
        error: () => {
          this.isSaveInProgress = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Revise los campos e intente nuevamente',
          });
        },
      });
  }

  updateConfig() {
    if (this.formConfig.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Revise los campos e intente nuevamente',
      });
      return;
    }
    this.isSaveInProgress = true;
    this.configService.createConfiguration(this.formConfig.value).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Guardado',
          detail: 'Configuracion actualizada correctamente',
        });
        this.isSaveInProgress = false;
        this.router.navigateByUrl('/');
      },
      error: () => {
        this.isSaveInProgress = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Revise los campos e intente nuevamente',
        });
      },
    });
  }

}
