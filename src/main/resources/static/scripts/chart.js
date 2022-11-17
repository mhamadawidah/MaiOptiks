function LineChart(container, datapoints) {
    const DATA_COUNT = 12;
    const labels = ["Januar","Feburar","März","April","Mai","Juni","Juli","August","September","Oktober","November","Dezember"];
    const datapoints = [0,100,0,300,350,0];
    const data = {
      labels: labels,
      datasets: [
        {
          label: 'Cubic interpolation (monotone)',
          data: datapoints,
          borderColor: Utils.CHART_COLORS.red,
          fill: false,
          cubicInterpolationMode: 'monotone',
          tension: 0.4
        }, {
          label: 'Cubic interpolation',
          data: datapoints,
          borderColor: Utils.CHART_COLORS.blue,
          fill: false,
          tension: 0.4
        }, {
          label: 'Linear interpolation (default)',
          data: datapoints,
          borderColor: Utils.CHART_COLORS.green,
          fill: false
        }
      ]
    };
};
