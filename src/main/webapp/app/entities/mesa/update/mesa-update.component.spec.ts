import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { MesaService } from '../service/mesa.service';
import { IMesa } from '../mesa.model';
import { MesaFormService } from './mesa-form.service';

import { MesaUpdateComponent } from './mesa-update.component';

describe('Mesa Management Update Component', () => {
  let comp: MesaUpdateComponent;
  let fixture: ComponentFixture<MesaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mesaFormService: MesaFormService;
  let mesaService: MesaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MesaUpdateComponent],
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
      .overrideTemplate(MesaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MesaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mesaFormService = TestBed.inject(MesaFormService);
    mesaService = TestBed.inject(MesaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const mesa: IMesa = { id: 10514 };

      activatedRoute.data = of({ mesa });
      comp.ngOnInit();

      expect(comp.mesa).toEqual(mesa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMesa>>();
      const mesa = { id: 9830 };
      jest.spyOn(mesaFormService, 'getMesa').mockReturnValue(mesa);
      jest.spyOn(mesaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mesa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mesa }));
      saveSubject.complete();

      // THEN
      expect(mesaFormService.getMesa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mesaService.update).toHaveBeenCalledWith(expect.objectContaining(mesa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMesa>>();
      const mesa = { id: 9830 };
      jest.spyOn(mesaFormService, 'getMesa').mockReturnValue({ id: null });
      jest.spyOn(mesaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mesa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mesa }));
      saveSubject.complete();

      // THEN
      expect(mesaFormService.getMesa).toHaveBeenCalled();
      expect(mesaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMesa>>();
      const mesa = { id: 9830 };
      jest.spyOn(mesaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mesa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mesaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
