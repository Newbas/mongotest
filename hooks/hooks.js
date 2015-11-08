var hooks = [
    {eventName : 'leaning forward', url : ''},
    {eventName : 'leaning backward', url : ''},
    {eventName : 'leaning left', url : ''},
    {eventName : 'leaning right', url : ''},
    {eventName : "Error : postion can't be estimated (chek threshold value)", url : ''},
    {eventName : 'Stood', url : ''},
    {eventName : 'Sat', url : ''}
];

{
    "eventName": "",
    "event": "",
    "url": "https://webhooks.local",
    "requestType": "POST",
    "headers": {"accept":"application/json"},

    "query": {
        "action": "create",
        "token": "YOUR_ACCESS_TOKEN_HERE",
    },
    "mydevices": true
}