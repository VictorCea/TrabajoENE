import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { PedidosProveedoresService } from '../service/pedidos-proveedores.service';
import { IPedidosProveedores } from '../pedidos-proveedores.model';
import { PedidosProveedoresFormService } from './pedidos-proveedores-form.service';

import { PedidosProveedoresUpdateComponent } from './pedidos-proveedores-update.component';

describe('PedidosProveedores Management Update Component', () => {
  let comp: PedidosProveedoresUpdateComponent;
  let fixture: ComponentFixture<PedidosProveedoresUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pedidosProveedoresFormService: PedidosProveedoresFormService;
  let pedidosProveedoresService: PedidosProveedoresService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PedidosProveedoresUpdateComponent],
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
      .overrideTemplate(PedidosProveedoresUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PedidosProveedoresUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pedidosProveedoresFormService = TestBed.inject(PedidosProveedoresFormService);
    pedidosProveedoresService = TestBed.inject(PedidosProveedoresService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pedidosProveedores: IPedidosProveedores = { id: 31162 };

      activatedRoute.data = of({ pedidosProveedores });
      comp.ngOnInit();

      expect(comp.pedidosProveedores).toEqual(pedidosProveedores);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPedidosProveedores>>();
      const pedidosProveedores = { id: 7220 };
      jest.spyOn(pedidosProveedoresFormService, 'getPedidosProveedores').mockReturnValue(pedidosProveedores);
      jest.spyOn(pedidosProveedoresService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pedidosProveedores });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pedidosProveedores }));
      saveSubject.complete();

      // THEN
      expect(pedidosProveedoresFormService.getPedidosProveedores).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pedidosProveedoresService.update).toHaveBeenCalledWith(expect.objectContaining(pedidosProveedores));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPedidosProveedores>>();
      const pedidosProveedores = { id: 7220 };
      jest.spyOn(pedidosProveedoresFormService, 'getPedidosProveedores').mockReturnValue({ id: null });
      jest.spyOn(pedidosProveedoresService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pedidosProveedores: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pedidosProveedores }));
      saveSubject.complete();

      // THEN
      expect(pedidosProveedoresFormService.getPedidosProveedores).toHaveBeenCalled();
      expect(pedidosProveedoresService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPedidosProveedores>>();
      const pedidosProveedores = { id: 7220 };
      jest.spyOn(pedidosProveedoresService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pedidosProveedores });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pedidosProveedoresService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
