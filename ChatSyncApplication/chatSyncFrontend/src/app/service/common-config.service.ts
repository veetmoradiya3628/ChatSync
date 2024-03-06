import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommonConfigService {
  public SERVER_URL: string = 'http://localhost:8080';
  public DEFAULT_AVATAR_IMAGE: string = 'https://png.pngtree.com/png-vector/20191101/ourmid/pngtree-cartoon-color-simple-male-avatar-png-image_1934459.jpg';  
}
