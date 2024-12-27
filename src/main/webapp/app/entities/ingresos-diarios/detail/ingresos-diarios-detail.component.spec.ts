import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { IngresosDiariosDetailComponent } from './ingresos-diarios-detail.component';

describe('IngresosDiarios Management Detail Component', () => {
  let comp: IngresosDiariosDetailComponent;
  let fixture: ComponentFixture<IngresosDiariosDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IngresosDiariosDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./ingresos-diarios-detail.component').then(m => m.IngresosDiariosDetailComponent),
              resolve: { ingresosDiarios: () => of({ id: 3106 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(IngresosDiariosDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IngresosDiariosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ingresosDiarios on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', IngresosDiariosDetailComponent);

      // THEN
      expect(instance.ingresosDiarios()).toEqual(expect.objectContaining({ id: 3106 }));
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
