import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OrdenesCocinaDetailComponent } from './ordenes-cocina-detail.component';

describe('OrdenesCocina Management Detail Component', () => {
  let comp: OrdenesCocinaDetailComponent;
  let fixture: ComponentFixture<OrdenesCocinaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrdenesCocinaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./ordenes-cocina-detail.component').then(m => m.OrdenesCocinaDetailComponent),
              resolve: { ordenesCocina: () => of({ id: 23895 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OrdenesCocinaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrdenesCocinaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ordenesCocina on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OrdenesCocinaDetailComponent);

      // THEN
      expect(instance.ordenesCocina()).toEqual(expect.objectContaining({ id: 23895 }));
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
