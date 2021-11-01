import React, { useState, useEffect, useRef } from 'react';
import { classNames } from 'primereact/utils';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import ApiService from '../service/ApiService';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { FileUpload } from 'primereact/fileupload';
import { Toolbar } from 'primereact/toolbar';

import { Dropdown } from 'primereact/dropdown';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import '../css/DataTableDemo.css';
import 'primeflex/primeflex.css';
import { connect } from 'react-redux'

//*Display as list Component

const WebService = (props) => {
    let emptyWebservice = {
        id: null,
        name: '',
        url: '',
        method: null
    };

    const GET = "GET";
    const [selectedService, setSelectedService] = useState(null);
    const services = [
        { name: 'https://', code: 'https://' },
        { name: 'http://', code: 'http://' }
    ];

    const onServiceChange = (e) => {
        console.log(e.value);
        setSelectedService(e.value);
    }

    const [webServices, setWebServices] = useState(null);
    const [webServiceDialog, setWebServiceDialog] = useState(false);
    const [deletewebServiceDialog, setDeletewebServiceDialog] = useState(false);
    const [deletewebServicesDialog, setDeleteWebServicesDialog] = useState(false);
    const [webService, setWebService] = useState(emptyWebservice);
    const [selectedWebServices, setSelectedWebServices] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [globalFilter, setGlobalFilter] = useState(null);
    const toast = useRef(null);
    const dt = useRef(null);
    const apiService = new ApiService();

    useEffect(() => {
        apiService.getWebServices(localStorage.getItem("token")).then(data => setWebServices(data))
            .catch(error => console.log("Error while trying to make api call " + JSON.stringify(error)));
    }, []); // eslint-disable-line react-hooks/exhaustive-deps

    const openNew = () => {
        setWebService(emptyWebservice);
        setSubmitted(false);
        setWebServiceDialog(true);
    }

    const hideDialog = () => {
        setSubmitted(false);
        setWebServiceDialog(false);
    }

    const hideDeleteProductDialog = () => {
        setDeletewebServiceDialog(false);
    }

    const hideDeleteProductsDialog = () => {
        setDeleteWebServicesDialog(false);
    }

    const saveWebService = () => {
        if (!webService.name || !webService.url || !selectedService.name) {
            toast.current.show({ severity: 'error', summary: 'Successful', detail: 'Error occurred while trying to create new Web service', life: 3000 });

            return;
        }
        setSubmitted(true);

        if (webService.name.trim()) {
            let _products = [];
            if (webServices) {
                _products = [...webServices];
            }
            webService.method = GET;
            let _product = { ...webService };
            // Make post request to save  Web service to Database
            if (webService.id <= 0) {
                apiService.createWebServices(webService.name, selectedService.name + webService.url,
                    GET, localStorage.getItem("token")).then((res) => {
                        if (res == 201) {
                            toast.current.show({ severity: 'success', summary: 'Successful', detail: 'A New Web service created Successfully', life: 3000 });

                        }
                        else {
                            toast.current.show({ severity: 'error', summary: 'Successful', detail: 'Error occurred while trying to create new Web service', life: 3000 });

                        }
                    });
            }
            else {
                apiService.updateWebService(webService.id, webService.name, selectedService.name + webService.url,
                    webService.method, localStorage.getItem("token")).then((res) => {
                        if (res == 201) {
                            toast.current.show({ severity: 'success', summary: 'Successful', detail: ' Web service updated Successfully', life: 3000 });
                        }
                        else {
                            //this.setState({ message: "Error Ocurrend while trying to create new Web service" });
                            // toast.current.show({ severity: 'error', summary: 'Error', detail: 'Error occurred while trying to update Web service', life: 3000 });
                        }
                    }).catch((error) => {
                        console.log("Status data " + error)
                        toast.current.show({ severity: 'error', summary: 'Error', detail: 'Error occurred while trying to update Web service', life: 3000 });
                    });
            }

            if (webService.id) {
                const index = findIndexById(webService.id);

                _products[index] = _product;
            }
            else {
                _product.image = 'product-placeholder.svg';
                _products.push(_product);
            }

            setWebServices(_products);
            setWebServiceDialog(false);
            setWebService(emptyWebservice);
        }
    }

    const editProduct = (product) => {
        let selectedServices = product.url.split("://")[0] + "://";
        setSelectedService(product.url.split("://")[0] + "://");
        product.url = product.url.split("://")[1];
        setWebService({ ...product });
        setWebServiceDialog(true);
    }

    const confirmDeleteProduct = (product) => {
        setWebService(product);
        setDeletewebServiceDialog(true);
    }

    const deleteProduct = () => {
        let _products = webServices.filter(val => val.id !== webService.id);
        setWebServices(_products);
        apiService.deleteWebService(webService.id, webService.name, webService.url,
            webService.method, localStorage.getItem("token")).then((res) => {
                if (res == 201) {
                    toast.current.show({ severity: 'success', summary: 'Successful', detail: ' Web service deleted Successfully', life: 3000 });
                }
                else {
                    //this.setState({ message: "Error Ocurrend while trying to create new Web service" });
                    // toast.current.show({ severity: 'error', summary: 'Error', detail: 'Error occurred while trying to update Web service', life: 3000 });
                }
            }).catch((error) => {
                toast.current.show({ severity: 'error', summary: 'Error', detail: 'Error occurred while trying to delete Web service', life: 3000 });
            });
        setDeletewebServiceDialog(false);
        setWebService(emptyWebservice);
        toast.current.show({ severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000 });
    }

    const findIndexById = (id) => {
        let index = -1;
        for (let i = 0; i < webServices.length; i++) {
            if (webServices[i].id === id) {
                index = i;
                break;
            }
        }
        return index;
    }

    const createId = () => {
        let id = '';
        let chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        for (let i = 0; i < 5; i++) {
            id += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return id;
    }

    const exportCSV = () => {
        dt.current.exportCSV();
    }

    const confirmDeleteSelected = () => {
        setDeleteWebServicesDialog(true);
    }

    const deleteSelectedProducts = () => {
        let _products = webServices.filter(val => !selectedWebServices.includes(val));
        setWebServices(_products);
        setDeleteWebServicesDialog(false);
        setSelectedWebServices(null);
        toast.current.show({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 });
    }

    const onCategoryChange = (e) => {
        let _product = { ...webService };
        _product['category'] = e.value;
        setWebService(_product);
    }

    const onInputChange = (e, name) => {
        const val = (e.target && e.target.value) || '';
        let _product = { ...webService };
        _product[`${name}`] = val;

        setWebService(_product);
    }

    const onInputNumberChange = (e, name) => {
        const val = e.value || 0;
        let _product = { ...webService };
        _product[`${name}`] = val;

        setWebService(_product);
    }

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                <Button label="New" icon="pi pi-plus" className="p-button-success p-mr-2" onClick={openNew} />
                <Button label="Delete" icon="pi pi-trash" className="p-button-danger" onClick={confirmDeleteSelected} disabled={!selectedWebServices || !selectedWebServices.length} />
            </React.Fragment>
        )
    }

    const rightToolbarTemplate = () => {
        return (
            <React.Fragment>
                <FileUpload mode="basic" accept="image/*" maxFileSize={1000000} label="Import" chooseLabel="Import" className="p-mr-2 p-d-inline-block" />
                <Button label="Export" icon="pi pi-upload" className="p-button-help" onClick={exportCSV} />
            </React.Fragment>
        )
    }

    const actionBodyTemplate = (rowData) => {
        return (
            <React.Fragment>
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-success p-mr-2" onClick={() => editProduct(rowData)} />
                <Button icon="pi pi-trash" className="p-button-rounded p-button-warning" onClick={() => confirmDeleteProduct(rowData)} />
            </React.Fragment>
        );
    }

    const header = (
        <div className="table-header">
            <h5 className="p-m-0">Manage Products</h5>
            <span className="p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.target.value)} placeholder="Search..." />
            </span>
        </div>
    );
    const productDialogFooter = (
        <React.Fragment>
            <Button label="Cancel" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
            <Button label="Save" icon="pi pi-check" className="p-button-text" onClick={saveWebService} />
        </React.Fragment>
    );
    const deleteProductDialogFooter = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteProductDialog} />
            <Button label="Yes" icon="pi pi-check" className="p-button-text" onClick={deleteProduct} />
        </React.Fragment>
    );
    const deleteProductsDialogFooter = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteProductsDialog} />
            <Button label="Yes" icon="pi pi-check" className="p-button-text" onClick={deleteSelectedProducts} />
        </React.Fragment>
    );

    return (
        <div className="datatable-crud-demo">
            <Toast ref={toast} />

            <div className="card">
                <Toolbar className="p-mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

                <DataTable ref={dt} value={webServices} selection={selectedWebServices} onSelectionChange={(e) => setSelectedWebServices(e.value)}
                    dataKey="id" paginator rows={10} rowsPerPageOptions={[5, 10, 25]}
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                    currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                    globalFilter={globalFilter}
                    header={header}>

                    <Column selectionMode="multiple" headerStyle={{ width: '3rem' }}></Column>
                    <Column field="name" header="Name" sortable></Column>
                    <Column field="url" header="Url" sortable></Column>
                    <Column field="method" header="Method" sortable></Column>

                    <Column body={actionBodyTemplate}></Column>
                </DataTable>
                <br /> <br /> <br /> <br />
            </div>

            <Dialog visible={webServiceDialog} style={{ width: '450px' }} header="Webservice Details" modal className="p-fluid" footer={productDialogFooter} onHide={hideDialog}>

                <div className="p-field">
                    <label htmlFor="name">Name</label>
                    <InputText id="name" value={webService.name} onChange={(e) => onInputChange(e, 'name')} required autoFocus className={classNames({ 'p-invalid': submitted && !webService.name })} />
                    {submitted && !webService.name && <small className="p-error">Name is required.</small>}
                </div>

                <div className="p-field">
                    <label htmlFor="url">Url</label>
                    <div className="p-inputgroup">
                        <Dropdown value={selectedService} options={services} onChange={onServiceChange} optionLabel="name" placeholder="Select a Service" />
                        <InputText id="url" placeholder="Website" value={webService.url} onChange={(e) => onInputChange(e, 'url')} required autoFocus className={classNames({ 'p-invalid': submitted && !webService.url })} />
                        {submitted && !webService.url && <small className="p-error">URL is required.</small>}
                    </div>
                </div>


                <div className="p-field">
                    <label htmlFor="method">Method</label>
                    <InputText id="method" /*value={webService.method}*/ value={"GET"} onChange={(e) => onInputChange(e, 'method')} disabled />
                </div>
            </Dialog>

            <Dialog visible={deletewebServiceDialog} style={{ width: '450px' }} header="Confirm" modal footer={deleteProductDialogFooter} onHide={hideDeleteProductDialog}>
                <div className="confirmation-content">
                    <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                    {webService && <span>Are you sure you want to delete <b>{webService.name}</b>?</span>}
                </div>
            </Dialog>

            <Dialog visible={deletewebServicesDialog} style={{ width: '450px' }} header="Confirm" modal footer={deleteProductsDialogFooter} onHide={hideDeleteProductsDialog}>
                <div className="confirmation-content">
                    <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                    {webService && <span>Are you sure you want to delete the selected products?</span>}
                </div>
            </Dialog>
        </div>
    );
}

const mapStateToProps = state => {
    //return { posts: state.posts }
    return {
        state
    }
}

const mapDispatchToProps = dispatch => {
    return {
        dispatch
    }
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(WebService)