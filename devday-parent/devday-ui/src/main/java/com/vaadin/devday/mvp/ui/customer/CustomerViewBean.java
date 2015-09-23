package com.vaadin.devday.mvp.ui.customer;

import java.util.List;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.devday.mvp.ui.AbstractView;
import com.vaadin.devday.mvp.ui.PresenterBinding;
import com.vaadin.devday.mvp.ui.customer.editor.CustomerEditorBean;
import com.vaadin.devday.mvp.ui.customer.event.CustomerEvent;
import com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType;
import com.vaadin.devday.mvp.ui.customer.event.EventTypeLiteral;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@CDIView("")
@PresenterBinding(presenter = CustomerViewPresenter.class)
public class CustomerViewBean extends AbstractView<CustomerViewPresenter>implements CustomerView, View {

	@Inject
	private javax.enterprise.event.Event<CustomerEvent> customerEventSource;

	@Inject
	@Any
	private Instance<CustomerEditorBean> editorInstantiator;

	private Table customerTable;
	private Button remove;

	private Action.Handler handler = new Handler() {

		@Override
		public Action[] getActions(Object target, Object sender) {
			if (target == null) {
				return new Action[] { new CustomerEvent(EventType.ADD, null) };
			} else {
				return new Action[] { new CustomerEvent(EventType.EDIT, (Customer) target),
						new CustomerEvent(EventType.REMOVE, (Customer) target) };
			}
		}

		@Override
		public void handleAction(Action action, Object sender, Object target) {
			fireEvent((CustomerEvent) action);
		}
	};

	public CustomerViewBean() {
		setSizeFull();

		customerTable = buildCustomerTable();
		customerTable.setSizeFull();

		Button add = new Button("Add", e -> fireEvent(new CustomerEvent(EventType.ADD, null)));
		add.setIcon(FontAwesome.PLUS);
		add.setId("customer.add");

		remove = new Button("Remove", e -> fireEvent(new CustomerEvent(EventType.REMOVE, getSelectedItem())));
		remove.setIcon(FontAwesome.TRASH_O);
		remove.setEnabled(false);
		remove.setId("customer.remove");

		HorizontalLayout buttonLayout = new HorizontalLayout(remove, add);
		buttonLayout.setSpacing(true);
		buttonLayout.setWidth(100, Unit.PERCENTAGE);
		buttonLayout.setExpandRatio(remove, 1);
		buttonLayout.setComponentAlignment(remove, Alignment.TOP_RIGHT);
		buttonLayout.setComponentAlignment(add, Alignment.TOP_RIGHT);

		VerticalLayout layout = new VerticalLayout(buttonLayout, customerTable);
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();

		layout.setExpandRatio(customerTable, 1);
		setCompositionRoot(layout);
	}

	private Customer getSelectedItem() {
		return (Customer) customerTable.getValue();
	}

	protected void fireEvent(CustomerEvent action) {
		CustomerEvent event = (CustomerEvent) action;
		customerEventSource.select(new EventTypeLiteral(event.getEventType())).fire(event);
	}

	private Table buildCustomerTable() {
		Table table = new Table();
		table.setSelectable(true);
		table.setContainerDataSource(new BeanItemContainer<Customer>(Customer.class));
		table.addActionHandler(handler);
		table.addValueChangeListener(e -> getPresenter().onTableItemSelected(getSelectedItem()));
		return table;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getPresenter().onViewEnter();
	}

	@Override
	public void populateCustomers(List<Customer> customers) {
		customerTable.setContainerDataSource(new BeanItemContainer<Customer>(Customer.class, customers));
	}

	@Override
	public void openCustomerEditor(Customer customer) {
		CustomerEditorBean customerEditor = editorInstantiator.get();
		BeanItem<Customer> customerBeanItem = (BeanItem<Customer>) customerTable.getContainerDataSource()
				.getItem(customer);
		customerEditor.setCustomer(customerBeanItem == null ? new BeanItem<Customer>(customer) : customerBeanItem);
	}

	@Override
	public void setRemoveButtonEnabled(boolean enabled) {
		remove.setEnabled(enabled);
	}

	@Override
	public void showRemovedNotification() {
		Notification.show("Data removed!", Type.TRAY_NOTIFICATION);
	}
}
