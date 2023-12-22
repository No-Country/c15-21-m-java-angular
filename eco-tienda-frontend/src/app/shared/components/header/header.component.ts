import { Component, inject } from '@angular/core';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'shared-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  private authService = inject(AuthService);
  isAutenticated(){
    const token = localStorage.getItem('token');
    if (token) {
      return true
    }
    return false;
  }
  onLogout() {
    this.authService.logout();
  }
}
