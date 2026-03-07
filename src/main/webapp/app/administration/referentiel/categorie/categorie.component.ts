import { Component, inject, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DEFAULT_SORT_DIR, FIRST_PAGE, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { GlobalService } from 'app/core/util/global.service';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { AdminPageHeaderComponent, AdminPageHeaderInput } from 'app/shared/components/admin-page-header/admin-page-header.component';
import { SharedFilterPaneComponent, SharedFilterPaneConfig } from 'app/shared/components/shared-filter-pane/shared-filter-pane.component';
import { SharedListTableComponent, SharedListTableInput } from 'app/shared/components/shared-list-table/shared-list-table.component';
import { SharedModalService } from 'app/shared/components/shared-modal/shared-modal.service';
import { EnumIconAlert } from 'app/shared/constants/liste.constants';
import { IEntiteFinanciere } from 'app/shared/model/principal/entite-financiere.model';
import { ICategorie } from 'app/shared/model/referentiel/categorie.model';

@Component({
  selector: 'jhi-categorie',
  imports: [AdminPageHeaderComponent, SharedListTableComponent, SharedFilterPaneComponent],
  templateUrl: './categorie.component.html',
  styleUrl: './categorie.component.scss',
})
export class CategorieComponent {
  @ViewChild(SharedFilterPaneComponent) sharedFilterPaneComponent?: SharedFilterPaneComponent;
  @ViewChild(SharedListTableComponent) sharedListTableComponent?: SharedListTableComponent;
  pageHeaderInput: AdminPageHeaderInput = {
    title: 'graceFinanceApp.categorie.home.title',
    items: [
      { label: 'main.breadCrumbs.admin', isActive: false },
      { label: 'main.breadCrumbs.referentiel', isActive: false },
      { label: 'main.breadCrumbs.categorie', isActive: false },
      { label: 'main.breadCrumbs.list', isActive: true },
    ],
    isWithAddButton: true,
    addButtonAuthorities: [
      // Authority.ADMIN,
      // Authority.CREATE_CLASSE_PRODUIT
    ],
    isWithExportDataButton: true,
    isWithRefreshDataButton: true,
    isWithFilterSettingsButton: true,
    isWithDeleteButton: false,
  };

  data: ICategorie[] = [];
  listEntiteFinanciere: IEntiteFinanciere[] = this.activatedRoute.snapshot.data[''] ?? [];

  filterConfig: SharedFilterPaneConfig = {
    //localStorageKey: EnumKeyFiltersLocalStorageByComponent.LISTE_CLASSE_PRODUIT,
    inputs: [
      // {
      //   index: 1,
      //   id: 'code',
      //   filterProperty: 'groupeProduitId',
      //   filterExpression: 'equals',
      //   type: 'select2',
      //   label: 'graceFinanceApp.categorie.fields.groupeProduit',
      //   placeholder: 'graceFinanceApp.categorie.createUpdate.placeholders.groupeProduit',
      //   options: this.listGroupeProduit.map(item => ({
      //     label: item.groupeProduitLibelle ?? `ID ${item.id}`,
      //     value: item.id,
      //   })),
      //   value: undefined,
      //   colClasses: 'col-12 col-md-4',
      // },
      {
        index: 2,
        id: 'code',
        filterProperty: 'code',
        filterExpression: 'contains',
        type: 'text',
        label: 'graceFinanceApp.categorie.fields.code',
        placeholder: 'graceFinanceApp.categorie.createUpdate.placeholders.code',
        value: undefined,
      },
      {
        index: 3,
        id: 'nom',
        filterProperty: 'nom',
        filterExpression: 'contains',
        type: 'text',
        label: 'graceFinanceApp.categorie.fields.nom',
        placeholder: 'graceFinanceApp.categorie.createUpdate.placeholders.nom',
        value: undefined,
      },
      {
        index: 4,
        id: 'actif',
        filterProperty: 'actif',
        filterExpression: 'equals',
        type: 'select2',
        label: 'graceFinanceApp.categorie.fields.actif',
        placeholder: 'graceFinanceApp.categorie.createUpdate.placeholders.actif',
        options: [
          { label: this.globalService.getInstantTranslate('main.labels.active'), value: true },
          { label: this.globalService.getInstantTranslate('main.labels.inactive'), value: false },
        ],
        value: undefined,
      },
    ],
  };
  showFilterPane = true;

  tableConfig: SharedListTableInput = {
    isWithCheckboxes: false,
    data: [],
    columns: [
      {
        key: 'id',
        label: 'graceFinanceApp.categorie.fields.id',
        sortable: true,
        order: -3,
        isInDetail: false,
        type: 'number',
        isNotInTableRow: true,
      },
      // {
      //   index: 1,
      //   id: 'entiteFinaciereId',
      //   filterProperty: 'entiteFinaciereId',
      //   filterExpression: 'equals',
      //   type: 'text',
      //   label: 'graceFinanceApp.categorie.fields.entiteFinaciere',
      //   placeholder: 'graceFinanceApp.categorie.createUpdate.placeholders.entiteFinaciere',
      //   options: this.listEntiteFinanciere.map(item => ({
      //     label: item.nom ?? `ID ${item.id}`,
      //     value: item.id,
      //   })),
      //   value: undefined,
      //   colClasses: 'col-12 col-md-4',
      // },
      {
        key: 'code',
        label: 'graceFinanceApp.categorie.fields.code',
        sortable: true,
        order: 2,
        isInDetail: true,
        type: 'text',
      },
      {
        key: 'nom',
        label: 'graceFinanceApp.categorie.fields.nom',
        sortable: true,
        order: 3,
        isInDetail: true,
        type: 'text',
      },
      {
        key: 'typeCategorie',
        label: 'graceFinanceApp.categorie.fields.typeCategorie',
        sortable: false,
        order: 4,
        isInDetail: true,
        isNotInTableRow: true,
        type: 'text',
      },
      {
        key: 'description',
        label: 'graceFinanceApp.categorie.fields.description',
        sortable: false,
        order: 5,
        isInDetail: true,
        isNotInTableRow: true,
        type: 'text',
      },
      {
        key: 'actif',
        label: 'graceFinanceApp.categorie.fields.categorieLibelleCn',
        sortable: false,
        order: 6,
        isInDetail: true,
        isNotInTableRow: true,
        type: 'text',
      },
      {
        key: 'createdBy',
        label: 'graceFinanceApp.categorie.fields.createdBy',
        sortable: false,
        order: 8,
        isInDetail: true,
        type: 'text',
        isNotInTableRow: true,
      },
      {
        key: 'createdDate',
        label: 'graceFinanceApp.categorie.fields.createdDate',
        sortable: false,
        order: 9,
        isInDetail: true,
        type: 'datetime',
        isNotInTableRow: true,
      },
      {
        key: 'lastModifiedBy',
        label: 'graceFinanceApp.categorie.fields.lastModifiedBy',
        sortable: false,
        order: 10,
        isInDetail: true,
        type: 'text',
        isNotInTableRow: true,
      },
      {
        key: 'lastModifiedDate',
        label: 'graceFinanceApp.categorie.fields.lastModifiedDate',
        sortable: false,
        order: 11,
        isInDetail: true,
        type: 'datetime',
        isNotInTableRow: true,
      },
    ],
    actions: [
      {
        id: 'details',
        label: 'main.buttons.view',
        icon: 'fa fa-eye',
        color: 'btn-info',
        authorities: [
          // Authority.ADMIN,
          // Authority.READ_CLASSE_PRODUIT
        ],
      },
      {
        id: 'edit',
        label: 'main.buttons.edit',
        icon: 'fa fa-edit',
        color: 'btn-primary',
        authorities: [
          // Authority.ADMIN,
          // Authority.UPDATE_CLASSE_PRODUIT
        ],
      },
      {
        id: 'delete',
        label: 'main.buttons.delete',
        icon: 'fa fa-trash',
        color: 'btn-danger',
        authorities: [
          // Authority.ADMIN,
          // Authority.DELETE_CLASSE_PRODUIT
        ],
      },
    ],
    displayDetailsDepliant: true,
    styleActions: 'button',
    striped: true,
    hover: true,
    responsive: true,
    colorClass: '',
    tableHeadColorClass: 'table-primary',
    tableBorderClass: 'table-bordered',
    additionalDivClass: 'p-3',
    totalItems: 0,
    itemsPerPage: ITEMS_PER_PAGE,
    page: FIRST_PAGE,
    pageSizes: [5, 10, 20, 50],
  };

  // exportedColumnsData: ExportedColumnData[] = [
  //   { key: 'id', label: 'graceFinanceApp.categorie.fields.id', type: 'number' },
  //   { key: 'categorieCode', label: 'graceFinanceApp.categorie.fields.categorieCode', type: 'string' },
  //   { key: 'categorieNom', label: 'graceFinanceApp.categorie.fields.categorieNom', type: 'string' },
  //   { key: 'categorieNomFrancais', label: 'graceFinanceApp.categorie.fields.categorieNomFrancais', type: 'string' },
  //   { key: 'categorieNomAnglais', label: 'graceFinanceApp.categorie.fields.categorieNomAnglais', type: 'string' },
  //   { key: 'categorieNomChinois', label: 'graceFinanceApp.categorie.fields.categorieNomChinois', type: 'string' },
  //   { key: 'sectionProduitLibelle', label: 'graceFinanceApp.categorie.fields.sectionProduitLibelle', type: 'string' },
  //   { key: 'divisionProduitLibelle', label: 'graceFinanceApp.categorie.fields.divisionProduitLibelle', type: 'string' },
  //   { key: 'groupeProduitLibelle', label: 'graceFinanceApp.categorie.fields.groupeProduitLibelle', type: 'string' },
  //   { key: 'categorieEnabled', label: 'graceFinanceApp.categorie.fields.categorieEnabled', type: 'boolean' },
  //   { key: 'createdBy', label: 'graceFinanceApp.categorie.fields.createdBy', type: 'string' },
  //   { key: 'createdDate', label: 'graceFinanceApp.categorie.fields.createdDate', type: 'date' },
  //   { key: 'lastModifiedBy', label: 'graceFinanceApp.categorie.fields.lastModifiedBy', type: 'string' },
  //   { key: 'lastModifiedDate', label: 'graceFinanceApp.categorie.fields.lastModifiedDate', type: 'date' },
  // ];

  currentFilterValues?: Record<string, any>;
  defaultSortKey = 'categorieLibelle';
  defaultSortDir: 'asc' | 'desc' = 'asc';
  currentSortKey = this.defaultSortKey;
  currentSortDir = this.defaultSortDir;
  private readonly modalService = inject(SharedModalService);

  constructor(
    private categorieService: CategorieService,
    private globalService: GlobalService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
  ) {}

  loadAll(page = FIRST_PAGE, size = ITEMS_PER_PAGE, filterValues?: Record<string, any>): void {
    const req = this.globalService.buildFilterDatas(
      this.filterConfig,
      filterValues,
      size,
      page - 1,
      this.currentSortKey ? [`${this.currentSortKey},${this.currentSortDir}`] : undefined,
    );
    // Adapter ici pour la pagination serveur si besoin
    this.categorieService.query(req ?? { page: page - 1, size }).subscribe(res => {
      const rawData = res.body ?? [];
      // On aplatit les libellés référentiels pour l'affichage du tableau ainsi que le libellé des options
      this.data = rawData.map(e => ({
        ...e,
        // groupeProduitLibelle: e.groupeProduit?.groupeProduitLibelle ?? '',
        // divisionProduitLibelle: e.groupeProduit?.divisionProduit?.divisionProduitLibelle ?? '',
        // sectionProduitLibelle: e.groupeProduit?.divisionProduit?.sectionProduit?.sectionProduitLibelle ?? '',
      }));
      this.tableConfig = {
        ...this.tableConfig,
        data: this.data,
        totalItems: Number(res.headers.get('X-Total-Count')) || this.data.length,
        itemsPerPage: size,
        page,
      };
    });
  }

  onTableAction(event: { actionId: string; row: any }): void {
    //   if (event.actionId === 'details') {
    //     this.onDetails(event.row);
    //   } else if (event.actionId === 'edit') {
    //     this.opencategorieForm(event.row);
    //   } else if (event.actionId === 'delete') {
    //     this.onDelete(event.row);
    //   } else if (event.actionId === 'enable' || event.actionId === 'disable') {
    //     this.onChangeState(event.row);
    //   }
  }

  onTablePageChange(event: { page: number; pageSize: number }): void {
    this.loadAll(event.page, event.pageSize, this.currentFilterValues);
  }

  onTableSort(event: { columnKey: string; sortEventId?: string }): void {
    if (this.currentSortKey === event.columnKey) {
      this.currentSortDir = this.globalService.reverseSortDir(this.currentSortDir);
    } else {
      this.currentSortKey = event.columnKey;
      this.currentSortDir = DEFAULT_SORT_DIR;
    }
    this.loadAll(FIRST_PAGE, undefined, this.currentFilterValues);
  }

  // onDetails(row: any): void {
  //   const detailPath = LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PRODUIT.CLASSE_PRODUIT.DETAIL.PATH.split('/').filter(Boolean);
  //   this.router.navigate([
  //     LISTE_ADMINISTRATION_ROUTES.PATH,
  //     LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PATH,
  //     LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PRODUIT.PATH,
  //     LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PRODUIT.CLASSE_PRODUIT.PATH,
  //     ...detailPath,
  //     row.id,
  //   ]);
  // }

  // onNew(): void {
  //   const addPath = LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PRODUIT.CLASSE_PRODUIT.ADD.PATH.split('/');
  //   this.router.navigate([
  //     LISTE_ADMINISTRATION_ROUTES.PATH,
  //     LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PATH,
  //     LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PRODUIT.PATH,
  //     LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PRODUIT.CLASSE_PRODUIT.PATH,
  //     ...addPath,
  //   ]);
  // }

  // onEdit(row: any): void {
  //   const editPath = LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PRODUIT.CLASSE_PRODUIT.EDIT.PATH.split('/').filter(Boolean);
  //   this.router.navigate([
  //     LISTE_ADMINISTRATION_ROUTES.PATH,
  //     LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PATH,
  //     LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PRODUIT.PATH,
  //     LISTE_ADMINISTRATION_ROUTES.REFERENTIEL.PRODUIT.CLASSE_PRODUIT.PATH,
  //     ...editPath,
  //     row.id,
  //   ]);
  // }

  onDelete(row: any): void {
    this.globalService
      .afficherAlertConfirmation({
        titre: 'graceFinanceApp.categorie.confirmation.deleteTitle',
        message: 'graceFinanceApp.categorie.confirmation.deletedMessage',
        icon: EnumIconAlert.QUESTION,
        showCancelButton: true,
        // confirmeBtnColor: 'success',
        isAddMentionIrreversible: true,
      })
      .subscribe(result => {
        if (result) {
          this.categorieService.delete(row.id).subscribe(() => {
            this.loadAll();
          });
        }
      });
  }

  // onChangeState(row: any): void {
  //   const categorie: Icategorie = row as Icategorie;
  //   this.globalService
  //     .afficherAlertConfirmation({
  //       titre: categorie.categorieEnabled
  //         ? 'graceFinanceApp.categorie.confirmation.disableTitle'
  //         : 'graceFinanceApp.categorie.confirmation.enableTitle',
  //       message: categorie.categorieEnabled
  //         ? 'graceFinanceApp.categorie.confirmation.disableMessage'
  //         : 'graceFinanceApp.categorie.confirmation.enableMessage',
  //       icon: EnumIconAlert.QUESTION,
  //       showCancelButton: true,
  //       // confirmeBtnColor: 'success',
  //       isAddMentionIrreversible: true,
  //     })
  //     .subscribe(result => {
  //       if (result && categorie.id) {
  //         this.categorieService.changeState(categorie.id).subscribe(() => {
  //           this.loadAll();
  //         });
  //       }
  //     });
  // }

  onFilterPaneFilter(filterValues: Record<string, any>): void {
    this.currentFilterValues = filterValues;
    this.loadAll(FIRST_PAGE, undefined, filterValues);
  }

  onFilterPaneReset(): void {
    this.currentFilterValues = undefined;
    this.currentSortKey = this.defaultSortKey;
    this.currentSortDir = this.defaultSortDir;
    this.tableConfig = {
      ...this.tableConfig,
      data: this.data,
      totalItems: this.data.length,
      page: FIRST_PAGE,
    };
    this.loadAll();
  }

  switchShowFilterPane(): void {
    this.showFilterPane = !this.showFilterPane;
  }

  // onExportData(): void {
  //   this.globalService.exportToExcel(this.exportedColumnsData, this.data, EnumExcelExportConstante.CLASSE_PRODUIT);
  // }

  // initializeFilterFromLocalStorage(): void {
  //   const storedFilters = this.globalService.getFiltersValuesFromLocalStorage(EnumKeyFiltersLocalStorageByComponent.LISTE_CLASSE_PRODUIT);
  //   if (storedFilters) {
  //     this.currentFilterValues = storedFilters;
  //     this.filterConfig = {
  //       ...this.filterConfig,
  //       inputs: this.filterConfig.inputs.map(input => ({
  //         ...input,
  //         value: storedFilters[input.id],
  //       })),
  //     };
  //     if (this.sharedFilterPaneComponent) {
  //       this.sharedFilterPaneComponent.initializeInputs();
  //     }
  //   }
  // }

  // opencategorieForm(row?: ICategorie): void {
  //   const title = row?.id ? 'main.admin.referentiel.categorie.edit.titleWithId' : 'main.admin.referentiel.categorie.add.title';
  //   const titleTransValues = row?.id ? { idLibelle: 'NOM', id: row.nom } : undefined;
  //   const modal = this.modalService.open(Creat, {
  //     title,
  //     titleTransValues,
  //     size: 'xl',
  //     actions: [
  //       { label: 'main.buttons.close', value: 'close', color: 'btn-secondary', icon: 'fa fa-ban' },
  //       { label: 'main.buttons.save', value: 'save', color: 'btn-primary', icon: 'fa fa-save' },
  //     ],
  //     data: {
  //       categorie: row ?? {},
  //       listEntiteFinanciere: this.listEntiteFinanciere,
  //     },
  //   });

  //   modal.afterClosed().subscribe({
  //     next: result => {
  //       if (result.confirmed) {
  //         this.loadAll();
  //       }
  //     },
  //   });
  // }
}
