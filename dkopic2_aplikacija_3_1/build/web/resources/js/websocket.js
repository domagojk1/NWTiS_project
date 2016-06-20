/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var aplikacija = "/" + document.location.pathname.split("/")[1];
var wsUri = "ws://" + document.location.host + aplikacija + "/nwtis";
var websocket = new WebSocket(wsUri);
websocket.onopen = function(evt) { };
websocket.onmessage = function(evt) { window.location.reload(); };

