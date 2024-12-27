import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MesaDetailComponent } from './mesa-detail.component';

describe('Mesa Management Detail Component', () => {
  let comp: MesaDetailComponent;
  let fixture: ComponentFixture<MesaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MesaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./mesa-detail.component').then(m => m.MesaDetailComponent),
              resolve: { mesa: () => of({ id: 9830 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MesaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MesaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mesa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MesaDetailComponent);

      // THEN
      expect(instance.mesa()).toEqual(expect.objectContaining({ id: 9830 }));
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
