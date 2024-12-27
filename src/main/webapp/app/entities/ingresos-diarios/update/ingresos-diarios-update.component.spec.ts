import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IngresosDiariosService } from '../service/ingresos-diarios.service';
import { IIngresosDiarios } from '../ingresos-diarios.model';
import { IngresosDiariosFormService } from './ingresos-diarios-form.service';

import { IngresosDiariosUpdateComponent } from './ingresos-diarios-update.component';

describe('IngresosDiarios Management Update Component', () => {
  let comp: IngresosDiariosUpdateComponent;
  let fixture: ComponentFixture<IngresosDiariosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ingresosDiariosFormService: IngresosDiariosFormService;
  let ingresosDiariosService: IngresosDiariosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [IngresosDiariosUpdateComponent],
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
      .overrideTemplate(IngresosDiariosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IngresosDiariosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ingresosDiariosFormService = TestBed.inject(IngresosDiariosFormService);
    ingresosDiariosService = TestBed.inject(IngresosDiariosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ingresosDiarios: IIngresosDiarios = { id: 19403 };

      activatedRoute.data = of({ ingresosDiarios });
      comp.ngOnInit();

      expect(comp.ingresosDiarios).toEqual(ingresosDiarios);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIngresosDiarios>>();
      const ingresosDiarios = { id: 3106 };
      jest.spyOn(ingresosDiariosFormService, 'getIngresosDiarios').mockReturnValue(ingresosDiarios);
      jest.spyOn(ingresosDiariosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ingresosDiarios });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ingresosDiarios }));
      saveSubject.complete();

      // THEN
      expect(ingresosDiariosFormService.getIngresosDiarios).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ingresosDiariosService.update).toHaveBeenCalledWith(expect.objectContaining(ingresosDiarios));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIngresosDiarios>>();
      const ingresosDiarios = { id: 3106 };
      jest.spyOn(ingresosDiariosFormService, 'getIngresosDiarios').mockReturnValue({ id: null });
      jest.spyOn(ingresosDiariosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ingresosDiarios: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ingresosDiarios }));
      saveSubject.complete();

      // THEN
      expect(ingresosDiariosFormService.getIngresosDiarios).toHaveBeenCalled();
      expect(ingresosDiariosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIngresosDiarios>>();
      const ingresosDiarios = { id: 3106 };
      jest.spyOn(ingresosDiariosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ingresosDiarios });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ingresosDiariosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
