import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Configuration } from '../models/Configuration';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private apiUrl = 'http://localhost:8081/api/v1/configuration/'

  constructor(private http:HttpClient) { }

  getConfigurations():Observable<Configuration[]> {
    return this.http.get<Configuration[]>(this.apiUrl);
  }

  getConfigurationByCode(code:number): Observable<Configuration> {
    return this.http.get<Configuration>('${this.apiUrl}/${code}');
  }

  createConfiguration(configuration:Configuration) {
    return this.http.post<Configuration>(this.apiUrl, configuration);
  }

}
