package com.vaadin.devday.mvp.ui.customer.editor;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.devday.mvp.ui.AbstractPopupView;
import com.vaadin.devday.mvp.ui.PresenterBinding;
import com.vaadin.devday.mvp.ui.customer.editor.CustomerEditorPresenter.BackendCommitFailedException;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@PresenterBinding(presenter = CustomerEditorPresenter.class)
public class CustomerEditorBean extends AbstractPopupView<CustomerEditorPresenter>implements CustomerEditor {
	private FormLayout form;
	private BeanFieldGroup<Customer> fieldGroup;

	private CommitHandler commmitHandler = new CommitHandler() {

		@Override
		public void preCommit(CommitEvent commitEvent) throws CommitException {
		}

		@Override
		public void postCommit(CommitEvent commitEvent) throws CommitException {
			try {
				getPresenter().onCommit(fieldGroup.getItemDataSource().getBean());
			} catch (BackendCommitFailedException e) {
				throw new CommitException(e);
			}
		}
	};

	public CustomerEditorBean() {
		form = new FormLayout();
		form.setMargin(true);
		form.setSpacing(true);

		fieldGroup = new BeanFieldGroup<>(Customer.class);
		fieldGroup.addCommitHandler(commmitHandler);

		TextField firstNameField = new TextField("First name");
		firstNameField.setNullRepresentation("");
		firstNameField.setId("field-firstname");

		TextField lastNameField = new TextField("Last name");
		lastNameField.setId("field-lastname");
		lastNameField.setNullRepresentation("");

		form.addComponents(firstNameField, lastNameField);

		fieldGroup.bind(firstNameField, "firstName");
		fieldGroup.bind(lastNameField, "lastName");

		Button save = new Button("Save", e -> getPresenter().onFormSaveClicked());
		save.addStyleName(ValoTheme.BUTTON_PRIMARY);
		Button cancel = new Button("Cancel", e -> close());

		HorizontalLayout buttonLayout = new HorizontalLayout(cancel, save);
		buttonLayout.setSpacing(true);
		buttonLayout.setWidth(100, Unit.PERCENTAGE);
		buttonLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		buttonLayout.setExpandRatio(cancel, 1);
		buttonLayout.setComponentAlignment(cancel, Alignment.TOP_RIGHT);

		VerticalLayout layout = new VerticalLayout(form, buttonLayout);
		layout.setSpacing(true);

		setHeightUndefined();

		setCompositionRoot(layout);
	}

	public void setCustomer(BeanItem<Customer> customer) {
		fieldGroup.setItemDataSource(customer);
	}

	@Override
	protected int getWindowPixelWidth() {
		return 350;
	}

	@Override
	protected boolean isModal() {
		return false;
	}

	@Override
	public void commitFormChanges() {
		try {
			fieldGroup.commit();
		} catch (CommitException e) {
			showSaveFailed();
		}
	}

	@Override
	public void showSaveFailed() {
		Notification.show("Saving failed", Type.ERROR_MESSAGE);
	}

	@Override
	public void showSaveSucceeded() {
		Notification saved = new Notification("Data saved!", Type.TRAY_NOTIFICATION);
		saved.setDelayMsec(5000);
		saved.show(Page.getCurrent());
	}
}
