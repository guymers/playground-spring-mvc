<%@ page session="false" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>Widgets</title>
		<link rel="stylesheet" type="text/css" href="http://cdn.sencha.io/ext-4.1.0-gpl/resources/css/ext-all.css" />
	</head>
	<body>
		<script type="text/javascript" src="http://cdn.sencha.io/ext-4.1.0-gpl/bootstrap.js"></script>
		<script type="text/javascript">
			Ext.require([
				'Ext.grid.*',
				'Ext.data.*',
				'Ext.util.*',
				'Ext.toolbar.Paging',
				'Ext.ModelManager',
				'Ext.tip.QuickTipManager'
			]);
			
			Ext.onReady(function() {
				// http://www.sencha.com/forum/showthread.php?176637-Ext.data.writer.Json-no-longer-respects-dateFormat
				Ext.JSON.encodeDate = function(date) {
					return Ext.Date.format(date, '"Y-m-d H:i:s"');
				};
				
				Ext.tip.QuickTipManager.init();
				
				var me = this;
				
				Ext.define('Widget', {
					extend: 'Ext.data.Model',
					idProperty: 'id',
					fields: [
						{
							name: 'id',
							type: 'int',
							useNull: true
						},
						{
							name: 'name',
							type: 'string',
							useNull: true
						},
						{
							name: 'createDate',
							type: 'date',
							dateFormat: 'Y-m-d H:i:s',
							useNull: true
						},
						{
							name: 'active',
							type: 'bool'
						}
					]/*,
					validations: [
						{
							type: 'length',
							field: 'email',
							min: 1
						},
						{
							type: 'length',
							field: 'first',
							min: 1
						},
						{
							type: 'length',
							field: 'last',
							min: 1
						}
					]*/
				});
				
				var store = Ext.create('Ext.data.Store', {
					model: 'Widget',
					
					autoLoad: true,
					remoteSort: true,
					pageSize: 50,
					
					proxy: {
						type: 'rest',
						url: 'widget',
						reader: {
							type: 'json',
							root: 'data',
							messageProperty: 'message'
						},
						writer: {
							type: 'json'
						}
					},
					sorters: [
						{
							property: 'id',
							direction: 'ASC'
						}
					]
				});
				
				var proxy = store.getProxy();
				proxy.on('exception', function(proxy, response, operation, e) {
					switch (operation.action) {
						case 'create':
						case 'update':
						case 'destroy':
							this.rejectChanges();
							break;
					}
					
					var message;
					
					try {
						var json = Ext.decode(response.responseText);
						message = json.message;
					} catch(e) {
						message = response.statusText;
					}
					
					Ext.Msg.alert('Error', message);
				}, store);
				
				var rowEditing = Ext.create('Ext.grid.plugin.RowEditing');
				
				var bbar = Ext.create('Ext.toolbar.Paging', {
					displayInfo: true,
					store: store
				});
				
				var grid = Ext.create('Ext.grid.Panel', {
					title: 'Widgets',
					iconCls: 'icon-user',
					renderTo: document.body,
					plugins: [rowEditing],
					width: 700,
					height: 500,
					frame: true,
					
					store: store,
					
					tbar: [
						{
							text: 'Add',
							iconCls: 'icon-add',
							handler: function() {
								var grid = me.grid;
								var editor = grid.editingPlugin;
								editor.cancelEdit();
								
								//var record = Ext.create('Widget', {});
								var record = new Widget();
								grid.getStore().insert(0, record);
								editor.startEdit(0, 0);
							}
						},
						'-',
						{
							itemId: 'delete',
							text: 'Delete',
							iconCls: 'icon-delete',
							disabled: true,
							handler: function() {
								var grid = me.grid;
								var selected = grid.getView().getSelectionModel().getSelection();
								
								if (selected) {
									var store = grid.getStore();
									store.remove(selected);
									store.sync();
								}
							}
						}
					],
					
					bbar: bbar,
					
					columns: [
						{
							text: 'ID',
							dataIndex: 'id',
							width: 40,
							sortable: true
						},
						{
							text: 'Name',
							dataIndex: 'name',
							flex: 1,
							sortable: true,
							field: {
								xtype: 'textfield',
								allowBlank: true
							}
						},
						{
							header: 'Create Date',
							dataIndex: 'createDate',
							width: 140,
							renderer: function(value) {
								return Ext.Date.format(value, 'Y-m-d');
							},
							sortable: true,
							field: {
								xtype: 'datefield',
								format: 'd/m/Y'
							}
						},
						{
							text: 'Active',
							dataIndex: 'active',
							width: 60,
							sortable: true,
							field: {
								xtype: 'checkbox'
							}
						}
					]
				});
				
				grid.on('selectionchange', function(selModel, selected) {
					var deleteButton = this.down('#delete');
					deleteButton.setDisabled(selected.length === 0);
				}, grid);
				
				grid.on('edit', function(editor, e) {
					this.getStore().sync();
				}, grid);
				
				grid.on('canceledit', function(editor, e) {
					this.getStore().rejectChanges();
				}, grid);
				
				me.grid = grid;
			});
		</script>
	</body>
</html>
