import { Component, ViewChild } from '@angular/core';
import {
  ApexAxisChartSeries,
  ApexChart,
  ChartComponent,
  ApexDataLabels,
  ApexPlotOptions,
  ApexYAxis,
  ApexLegend,
  ApexStroke,
  ApexXAxis,
  ApexFill,
  ApexTooltip
} from "ng-apexcharts";
import { DashboardDataType } from 'src/app/shared/interfaces/pagination.interface';
import { DashboardService } from 'src/app/shared/services/dashboard.service';
import { UtilityService } from 'src/app/shared/services/utility.service';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  dataLabels: ApexDataLabels;
  plotOptions: ApexPlotOptions;
  yaxis: ApexYAxis;
  xaxis: ApexXAxis;
  fill: ApexFill;
  tooltip: ApexTooltip;
  stroke: ApexStroke;
  legend: ApexLegend;
};

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  sessionUser: any;

  @ViewChild("chart") chart!: ChartComponent;
  public chartOptions!: Partial<ChartOptions> | any;
  dashboardData: DashboardDataType = {
    totalUser: null,
    totalRestaurant: null,
    totalOrder: null,
    totalEmployee: null,
    totalOrderDelivered: null,
    totalPendingOrder: null,
    totalRevenue: null,
  }


  constructor(
    private _dashboardService: DashboardService,
    private _utilityService: UtilityService,
  ) {
    this.chartOptions = {
      series: [
        {
          name: "Earned Points",
          data: [44, 55, 57, 56, 61, 58, 63, 60, 66, 60, 44, 68]
        },
        {
          name: "Redeemed Points",
          data: [76, 85, 101, 98, 87, 105, 91, 114, 94, 100, 116, 120]
        }
      ],
      chart: {
        type: "bar",
        height: 350
      },
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: "55%"
        }
      },
      dataLabels: {
        enabled: true
      },
      stroke: {
        show: true,
        width: 2,
        colors: ["transparent"]
      },
      xaxis: {
        categories: [
          "Jan",
          "Feb",
          "Mar",
          "Apr",
          "May",
          "Jun",
          "Jul",
          "Aug",
          "Sep",
          "Oct",
          "Nov",
          "Dec"
        ]
      },
      yaxis: {
        title: {
          text: "Points"
        }
      },
      fill: {
        opacity: 1
      },
      tooltip: {
        y: {
          formatter: function (val: any) {
            return val;
          }
        }
      }
    };

    this._dashboardService.getDashboard().then((response: any) => {
      this.dashboardData = response.body.data;
    })

    this.sessionUser = this._utilityService.getAuthUser();
  }
}

