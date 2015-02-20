
Parse.Cloud.define('synchData', function (request, response) {
    var DeviceQuery = new Parse.Query('Devices');
    DeviceQuery.equalTo('deviceId', request.params.deviceId);
    DeviceQuery.first({
        success: function (device) {
            if (!device) {
                var Device = Parse.Object.extend("Devices");
                device = new Device();
                device.set("deviceId", request.params.deviceId);
            }
            device.set("salary", parseInt('' + request.params.salary, 10));
            device.save({
                success: function(err) {
                    response.success({
                        status: 'success',
                        message: 'Data synchronized'
                    });
                },
                error: function(obj, err) {
                    response.error({
                        status: 'error',
                        message: 'Could not save data: ' + err.message
                    });
                }
            });
        },
        error: function (devive, error) {
            response.error({
                status: 'error',
                message: 'Could not synchronize data'
            });
        }
    });
});
