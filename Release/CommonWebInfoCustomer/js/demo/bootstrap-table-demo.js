/*!
 * Remark (http://getbootstrapadmin.com/remark)
 * Copyright 2015 amazingsurge
 * Licensed under the Themeforest Standard Licenses
 */
function cellStyle(value, row, index) {
  var classes = ['active', 'success', 'info', 'warning', 'danger'];

  if (index % 2 === 0 && index / 2 < classes.length) {
    return {
      classes: classes[index / 2]
    };
  }
  return {};
}

function rowStyle(row, index) {
  var classes = ['active', 'success', 'info', 'warning', 'danger'];

  if (index % 2 === 0 && index / 2 < classes.length) {
    return {
      classes: classes[index / 2]
    };
  }
  return {};
}

function scoreSorter(a, b) {
  if (a > b) return 1;
  if (a < b) return -1;
  return 0;
}

function nameFormatter(value) {
  return value + '<i class="icon wb-book" aria-hidden="true"></i> ';
}

function starsFormatter(value) {
  return '<i class="icon wb-star" aria-hidden="true"></i> ' + value;
}

function queryParams() {
  return {
    type: 'owner',
    sort: 'updated',
    direction: 'desc',
    per_page: 100,
    page: 1
  };
}

function buildTable($el, cells, rows) {
  var i, j, row,
    columns = [],
    data = [];

  for (i = 0; i < cells; i++) {
    columns.push({
      field: '字段' + i,
      title: '单元' + i
    });
  }
  for (i = 0; i < rows; i++) {
    row = {};
    for (j = 0; j < cells; j++) {
      row['字段' + j] = 'Row-' + i + '-' + j;
    }
    data.push(row);
  }
  $el.bootstrapTable('destroy').bootstrapTable({
    columns: columns,
    data: data,
    iconSize: 'outline',
    icons: {
      columns: 'glyphicon-list'
    }
  });
}

(function(document, window, $) {
  'use strict';

  // Example Bootstrap Table Events
  // ------------------------------
  (function() {
    $('#exampleTableEvents').bootstrapTable({
      url: "../../js/demo/bootstrap_table_test.json",
      search: true,
      pagination: true,
      showRefresh: true,
      showToggle: true,
      showColumns: true,
      iconSize: 'outline',
      toolbar: '#exampleTableEventsToolbar',
      icons: {
        refresh: 'glyphicon-repeat',
        toggle: 'glyphicon-list-alt',
        columns: 'glyphicon-list'
      }
    });

    var $result = $('#examplebtTableEventsResult');

    $('#exampleTableEvents').on('all.bs.table', function(e, name, args) {
        console.log('Event:', name, ', data:', args);
      })
      
  })();
})(document, window, jQuery);
