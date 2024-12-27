import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PedidosProveedoresDetailComponent } from './pedidos-proveedores-detail.component';

describe('PedidosProveedores Management Detail Component', () => {
  let comp: PedidosProveedoresDetailComponent;
  let fixture: ComponentFixture<PedidosProveedoresDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PedidosProveedoresDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./pedidos-proveedores-detail.component').then(m => m.PedidosProveedoresDetailComponent),
              resolve: { pedidosProveedores: () => of({ id: 7220 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PedidosProveedoresDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PedidosProveedoresDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pedidosProveedores on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PedidosProveedoresDetailComponent);

      // THEN
      expect(instance.pedidosProveedores()).toEqual(expect.objectContaining({ id: 7220 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
