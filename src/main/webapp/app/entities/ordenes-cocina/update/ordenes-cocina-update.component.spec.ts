import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { OrdenesCocinaService } from '../service/ordenes-cocina.service';
import { IOrdenesCocina } from '../ordenes-cocina.model';
import { OrdenesCocinaFormService } from './ordenes-cocina-form.service';

import { OrdenesCocinaUpdateComponent } from './ordenes-cocina-update.component';

describe('OrdenesCocina Management Update Component', () => {
  let comp: OrdenesCocinaUpdateComponent;
  let fixture: ComponentFixture<OrdenesCocinaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ordenesCocinaFormService: OrdenesCocinaFormService;
  let ordenesCocinaService: OrdenesCocinaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrdenesCocinaUpdateComponent],
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
      .overrideTemplate(OrdenesCocinaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrdenesCocinaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ordenesCocinaFormService = TestBed.inject(OrdenesCocinaFormService);
    ordenesCocinaService = TestBed.inject(OrdenesCocinaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ordenesCocina: IOrdenesCocina = { id: 16508 };

      activatedRoute.data = of({ ordenesCocina });
      comp.ngOnInit();

      expect(comp.ordenesCocina).toEqual(ordenesCocina);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdenesCocina>>();
      const ordenesCocina = { id: 23895 };
      jest.spyOn(ordenesCocinaFormService, 'getOrdenesCocina').mockReturnValue(ordenesCocina);
      jest.spyOn(ordenesCocinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordenesCocina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ordenesCocina }));
      saveSubject.complete();

      // THEN
      expect(ordenesCocinaFormService.getOrdenesCocina).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ordenesCocinaService.update).toHaveBeenCalledWith(expect.objectContaining(ordenesCocina));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdenesCocina>>();
      const ordenesCocina = { id: 23895 };
      jest.spyOn(ordenesCocinaFormService, 'getOrdenesCocina').mockReturnValue({ id: null });
      jest.spyOn(ordenesCocinaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordenesCocina: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ordenesCocina }));
      saveSubject.complete();

      // THEN
      expect(ordenesCocinaFormService.getOrdenesCocina).toHaveBeenCalled();
      expect(ordenesCocinaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdenesCocina>>();
      const ordenesCocina = { id: 23895 };
      jest.spyOn(ordenesCocinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordenesCocina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ordenesCocinaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
