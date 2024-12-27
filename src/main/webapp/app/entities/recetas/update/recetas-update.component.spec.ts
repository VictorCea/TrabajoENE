import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { RecetasService } from '../service/recetas.service';
import { IRecetas } from '../recetas.model';
import { RecetasFormService } from './recetas-form.service';

import { RecetasUpdateComponent } from './recetas-update.component';

describe('Recetas Management Update Component', () => {
  let comp: RecetasUpdateComponent;
  let fixture: ComponentFixture<RecetasUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let recetasFormService: RecetasFormService;
  let recetasService: RecetasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RecetasUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RecetasUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RecetasUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    recetasFormService = TestBed.inject(RecetasFormService);
    recetasService = TestBed.inject(RecetasService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const recetas: IRecetas = { id: 20001 };

      activatedRoute.data = of({ recetas });
      comp.ngOnInit();

      expect(comp.recetas).toEqual(recetas);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecetas>>();
      const recetas = { id: 7707 };
      jest.spyOn(recetasFormService, 'getRecetas').mockReturnValue(recetas);
      jest.spyOn(recetasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recetas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recetas }));
      saveSubject.complete();

      // THEN
      expect(recetasFormService.getRecetas).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(recetasService.update).toHaveBeenCalledWith(expect.objectContaining(recetas));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecetas>>();
      const recetas = { id: 7707 };
      jest.spyOn(recetasFormService, 'getRecetas').mockReturnValue({ id: null });
      jest.spyOn(recetasService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recetas: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recetas }));
      saveSubject.complete();

      // THEN
      expect(recetasFormService.getRecetas).toHaveBeenCalled();
      expect(recetasService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecetas>>();
      const recetas = { id: 7707 };
      jest.spyOn(recetasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recetas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(recetasService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
