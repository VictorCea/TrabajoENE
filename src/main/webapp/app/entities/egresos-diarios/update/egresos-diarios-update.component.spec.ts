import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { EgresosDiariosService } from '../service/egresos-diarios.service';
import { IEgresosDiarios } from '../egresos-diarios.model';
import { EgresosDiariosFormService } from './egresos-diarios-form.service';

import { EgresosDiariosUpdateComponent } from './egresos-diarios-update.component';

describe('EgresosDiarios Management Update Component', () => {
  let comp: EgresosDiariosUpdateComponent;
  let fixture: ComponentFixture<EgresosDiariosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let egresosDiariosFormService: EgresosDiariosFormService;
  let egresosDiariosService: EgresosDiariosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EgresosDiariosUpdateComponent],
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
      .overrideTemplate(EgresosDiariosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EgresosDiariosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    egresosDiariosFormService = TestBed.inject(EgresosDiariosFormService);
    egresosDiariosService = TestBed.inject(EgresosDiariosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const egresosDiarios: IEgresosDiarios = { id: 22661 };

      activatedRoute.data = of({ egresosDiarios });
      comp.ngOnInit();

      expect(comp.egresosDiarios).toEqual(egresosDiarios);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEgresosDiarios>>();
      const egresosDiarios = { id: 1591 };
      jest.spyOn(egresosDiariosFormService, 'getEgresosDiarios').mockReturnValue(egresosDiarios);
      jest.spyOn(egresosDiariosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ egresosDiarios });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: egresosDiarios }));
      saveSubject.complete();

      // THEN
      expect(egresosDiariosFormService.getEgresosDiarios).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(egresosDiariosService.update).toHaveBeenCalledWith(expect.objectContaining(egresosDiarios));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEgresosDiarios>>();
      const egresosDiarios = { id: 1591 };
      jest.spyOn(egresosDiariosFormService, 'getEgresosDiarios').mockReturnValue({ id: null });
      jest.spyOn(egresosDiariosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ egresosDiarios: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: egresosDiarios }));
      saveSubject.complete();

      // THEN
      expect(egresosDiariosFormService.getEgresosDiarios).toHaveBeenCalled();
      expect(egresosDiariosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEgresosDiarios>>();
      const egresosDiarios = { id: 1591 };
      jest.spyOn(egresosDiariosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ egresosDiarios });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(egresosDiariosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
