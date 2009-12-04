package com.brentryan.client.widgets.dialogs;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.core.Connection;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ButtonConfig;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.TabPanelItem;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarTextItem;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.TabPanelItemListenerAdapter;
import com.gwtext.client.widgets.form.FieldSetConfig;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormActionConfig;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TextFieldConfig;
import com.gwtext.client.widgets.form.VType;
import com.gwtext.client.widgets.form.event.FormListener;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.ContentPanelConfig;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;


public class LoginDialog extends Composite
{
    private static final LoginDialogConstants CONSTANTS = (LoginDialogConstants) GWT.create(LoginDialogConstants.class);

    private LayoutDialog dialog;

    private final Form signInForm;

    private final Form registerForm;

    private TextField username;

    public LoginDialog()
    {
        LayoutRegionConfig center = new LayoutRegionConfig() {
            {
                setAutoScroll(true);
                setTabPosition("top");
                setCloseOnTab(true);
                setAlwaysShowTabs(true);
            }
        };

        dialog = new LayoutDialog(new LayoutDialogConfig() {
            {
                setModal(true);
                setWidth(500);
                setHeight(300);
                setShadow(true);
                setResizable(false);
                setClosable(false);
                setProxyDrag(true);
                setTitle(CONSTANTS.SignInTitle());
            }
        }, center);

        final BorderLayout layout = dialog.getLayout();
        layout.beginUpdate();

        ContentPanel signInPanel = new ContentPanel(Ext.generateId(), CONSTANTS.SignInTitle());
        signInForm = getSignInForm();

        VerticalPanel signInWrapper = new VerticalPanel() {
            {
                setSpacing(30);
                setWidth("100%");
                setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            }
        };
        signInWrapper.add(signInForm);

        signInPanel.add(signInWrapper);
        layout.add(LayoutRegionConfig.CENTER, signInPanel);

        ContentPanel registerPanel = new ContentPanel(Ext.generateId(), new ContentPanelConfig() {
            {
                setTitle(CONSTANTS.RegisterTitle());
                setBackground(true);
            }
        });

        registerForm = getRegistrationForm();
        VerticalPanel registerWrapper = new VerticalPanel() {
            {
                setSpacing(20);
                setWidth("100%");
                setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            }
        };
        registerWrapper.add(registerForm);

        registerPanel.add(registerWrapper);
        layout.add(LayoutRegionConfig.CENTER, registerPanel);

        final ButtonListenerAdapter aboutListener = new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e)
            {
                new AboutDialog();
            }
        };

        final Toolbar tb = new Toolbar(Ext.generateId());
        tb.addButton(new ToolbarButton(CONSTANTS.About(), new ButtonConfig() {
            {
                setButtonListener(aboutListener);
            }
        }));
        tb.addSeparator();
        tb.addItem(new ToolbarTextItem("Copyright &copy; 2007"));

        ContentPanel infoPanel = new ContentPanel(Ext.generateId(), new ContentPanelConfig() {
            {
                setTitle(CONSTANTS.InfoTitle());
                setClosable(true);
                setBackground(true);
                setToolbar(tb);
            }
        });
        infoPanel.setContent(CONSTANTS.Info());

        layout.add(LayoutRegionConfig.CENTER, infoPanel);
        layout.endUpdate();

        final Button signInBtn = dialog.addButton(CONSTANTS.SignIn());
        signInBtn.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e)
            {
                signInForm.submit(new FormActionConfig() {
                    {
                        setMethod(Connection.POST);
                        setUrl("/server/user/handleLogin");
                        setWaitMsg(CONSTANTS.SignInMask());
                    }
                });
            }
        });
        signInBtn.focus();

        final Button registerBtn = dialog.addButton(CONSTANTS.Register());
        registerBtn.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(Button button, EventObject e)
            {
                registerForm.submit();
            }
        });
        registerBtn.hide();

        TabPanel tabPanel = layout.getRegion(LayoutRegionConfig.CENTER).getTabs();

        tabPanel.getTab(0).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
            public void onActivate(TabPanelItem tab)
            {
                dialog.setTitle(CONSTANTS.SignInTitle());
                registerBtn.hide();
                signInBtn.show();
                signInBtn.focus();
            }
        });

        tabPanel.getTab(1).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
            public void onActivate(TabPanelItem tab)
            {
                dialog.setTitle(CONSTANTS.RegisterTitle());
                signInBtn.hide();
                registerBtn.show();
                tab.getTextEl().highlight();
            }
        });

        tabPanel.getTab(2).addTabPanelItemListener(new TabPanelItemListenerAdapter() {
            public void onActivate(TabPanelItem tab)
            {
                dialog.setTitle(CONSTANTS.InfoTitle());
                registerBtn.hide();
                signInBtn.hide();
            }
        });
        
        initWidget(dialog);
    }

    public void addSignInFormListener(FormListener listener)
    {
        signInForm.addFormListenerListener(listener);
    }

    public void addRegisterFormListener(FormListener listener)
    {
        registerForm.addFormListenerListener(listener);
    }

    private Form getRegistrationForm()
    {
        final Form form = new Form(new FormConfig() {
            {
                setWidth(400);
                setLabelWidth(75);
                setLabelAlign(Position.RIGHT);
            }
        });

        form.fieldset(new FieldSetConfig() {
            {
                setLegend(CONSTANTS.RegisterTitle());
            }
        });
        form.add(new TextField(new TextFieldConfig() {
            {
                setFieldLabel(CONSTANTS.Username());
                setName("uname");
                setWidth(200);
                setAllowBlank(false);
            }
        }));

        form.add(new TextField(new TextFieldConfig() {
            {
                setFieldLabel(CONSTANTS.FirstName());
                setName("fname");
                setWidth(200);
                setAllowBlank(false);
            }
        }));

        form.add(new TextField(new TextFieldConfig() {
            {
                setFieldLabel(CONSTANTS.Password());
                setName("password");
                setPassword(true);
                setAllowBlank(false);
                setWidth(200);
            }
        }));

        form.add(new TextField(new TextFieldConfig() {
            {
                setFieldLabel(CONSTANTS.Email());
                setName("email");
                setVtype(VType.EMAIL);
                setWidth(200);
            }
        }));
        form.end();
        form.render();
        return form;
    }

    private Form getSignInForm()
    {
        final Form form = new Form(new FormConfig() {
            {
                setWidth(300);
                setLabelWidth(75);
            }
        });

        TextField username = new TextField(new TextFieldConfig() {
            {
                setFieldLabel(CONSTANTS.Username());
                setName("username");
                setWidth(175);
                setAllowBlank(false);
            }
        });

        this.setUsername(username);

        form.fieldset(CONSTANTS.SignInTitle());
        form.add(username);

        form.add(new TextField(new TextFieldConfig() {
            {
                setFieldLabel(CONSTANTS.Password());
                setName("password");
                setWidth(175);
                setPassword(true);
                setAllowBlank(false);
            }
        }));

        form.end();
        form.render();
        return form;
    }

    public void show()
    {
        if (dialog != null)
        {
            dialog.show();
        }
    }

    public void hide()
    {
        if (dialog != null)
        {
            dialog.hide();
        }
    }

    private void setUsername(TextField username)
    {
        this.username = username;

    }

    public TextField getUsername()
    {
        return username;
    }

    public String getUsernameAsString()
    {
        return username.getValueAsString();
    }

    public void destroy()
    {
        if (dialog != null)
        {
            dialog.destroy();
        }
    }
}
