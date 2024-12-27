import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EgresosDiariosDetailComponent } from './egresos-diarios-detail.component';

describe('EgresosDiarios Management Detail Component', () => {
  let comp: EgresosDiariosDetailComponent;
  let fixture: ComponentFixture<EgresosDiariosDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EgresosDiariosDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./egresos-diarios-detail.component').then(m => m.EgresosDiariosDetailComponent),
              resolve: { egresosDiarios: () => of({ id: 1591 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EgresosDiariosDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EgresosDiariosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load egresosDiarios on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EgresosDiariosDetailComponent);

      // THEN
      expect(instance.egresosDiarios()).toEqual(expect.objectContaining({ id: 1591 }));
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
