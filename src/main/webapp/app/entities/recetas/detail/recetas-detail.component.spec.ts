import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { RecetasDetailComponent } from './recetas-detail.component';

describe('Recetas Management Detail Component', () => {
  let comp: RecetasDetailComponent;
  let fixture: ComponentFixture<RecetasDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecetasDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./recetas-detail.component').then(m => m.RecetasDetailComponent),
              resolve: { recetas: () => of({ id: 7707 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RecetasDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecetasDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load recetas on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RecetasDetailComponent);

      // THEN
      expect(instance.recetas()).toEqual(expect.objectContaining({ id: 7707 }));
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
