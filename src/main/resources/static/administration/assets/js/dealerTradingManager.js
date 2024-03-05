var TableProductListAll = document.getElementById("table-product-list-all");
function getChartColorsArray(e) {
  if (null !== document.getElementById(e)) {
    e = document.getElementById(e).getAttribute("data-colors");
    if (e)
      return (e = JSON.parse(e)).map(function (e) {
        var t = e.replace(" ", "");
        return -1 === t.indexOf(",")
          ? getComputedStyle(document.documentElement).getPropertyValue(t) || t
          : 2 == (e = e.split(",")).length
          ? "rgba(" +
            getComputedStyle(document.documentElement).getPropertyValue(e[0]) +
            "," +
            e[1] +
            ")"
          : t;
      });
  }
}
TableProductListAll &&
  new gridjs.Grid({
    columns: [
      {
        name: "거래품목",
        width: "330px",
        formatter: function (e) {
          return gridjs.html(
            '<div class="d-flex align-items-center"><div class="flex-shrink-0 me-3"><div class="avatar-sm bg-light rounded p-1"><img src="/administration/assets/images/products/' +
              e[0] +
              '" alt="" class="img-fluid d-block"></div></div><div class="flex-grow-1"><h5 class="fs-14 mb-1"><a href="/admin/dealerTradingManagerDetail" class="text-body">' +
              e[1] +
              '</a></h5><p class="text-muted mb-0">Category : <span class="fw-medium">' +
              e[2] +
              "</span></p></div></div>"
          );
        },
      },
      { name: "제품금액", width: "101px" },
      { 
		  name: "주문상태", 
      	  width: "114px",
      	  formatter: function (e) {
          return gridjs.html(
              //'<span><a href="/admin/memberPaymentManagerDetail>' + e + '</a></span>'
              '<div class="d-flex align-items-center"><div class="flex-grow-1"><h5 class="fs-14 mb-1"><a href="/admin/dealerTradingManagerDetail" class="text-body">' +
               e +
              '</a></h5></div></div>' 
          );
        },},
      {
        name: "별점",
        width: "105px",
        formatter: function (e) {
          return gridjs.html(
            '<span class="badge bg-light text-body fs-12 fw-medium"><i class="mdi mdi-star text-warning me-1"></i>' +
              e +
              "</span></td>"
          );
        },
      },
      {
        name: "구매일",
        width: "220px",
        formatter: function (e) {
          return gridjs.html(
            e[0] + '<small class="text-muted ms-1">' + e[1] + "</small>"
          );
        },
      },
    ],
    className: { th: "text-muted" },
    pagination: { limit: 10 },
    sort: !0,
    data: [
      [
        ["img-2.png", "제품명", "대분류/중분류/소분류/브랜드"],
        "제품금액",
        "Paid",
        "4.3",
        ["2024.01.01", "11:30AM"],
      ],
      [
        ["img-2.png", "제품명", "대분류/중분류/소분류/브랜드"],
        "제품금액",
        "Paid",
        "4.3",
        ["2024.01.01", "11:30AM"],
      ],
      [
        ["img-2.png", "제품명", "대분류/중분류/소분류/브랜드"],
        "제품금액",
        "Paid",
        "4.3",
        ["2024.01.01", "11:30AM"],
      ],
      [
        ["img-2.png", "제품명", "대분류/중분류/소분류/브랜드"],
        "제품금액",
        "Paid",
        "4.3",
        ["2024.01.01", "11:30AM"],
      ],
      [
        ["img-2.png", "제품명", "대분류/중분류/소분류/브랜드"],
        "제품금액",
        "Paid",
        "4.3",
        ["2024.01.01", "11:30AM"],
      ],
      [
        ["img-2.png", "제품명", "대분류/중분류/소분류/브랜드"],
        "제품금액",
        "Paid",
        "4.3",
        ["2024.01.01", "11:30AM"],
      ],
      [
        ["img-2.png", "제품명", "대분류/중분류/소분류/브랜드"],
        "제품금액",
        "Paid",
        "4.3",
        ["2024.01.01", "11:30AM"],
      ],
      [
        ["img-2.png", "제품명", "대분류/중분류/소분류/브랜드"],
        "제품금액",
        "Paid",
        "4.3",
        ["2024.01.01", "11:30AM"],
      ],
      [
        ["img-2.png", "제품명", "대분류/중분류/소분류/브랜드"],
        "제품금액",
        "Paid",
        "4.3",
        ["2024.01.01", "11:30AM"],
      ],
      [
        ["img-2.png", "제품명", "대분류/중분류/소분류/브랜드"],
        "제품금액",
        "Paid",
        "4.3",
        ["2024.01.01", "11:30AM"],
      ],
    ],
  }).render(document.getElementById("table-product-list-all"));
var options,
  chart,
  swiper,
  linechartcustomerColors = getChartColorsArray("customer_impression_charts"),
  counterValue =
    (linechartcustomerColors &&
      ((options = {
        series: [
          {
            name: "Orders",
            type: "area",
            data: [34, 65, 46, 68, 49, 61, 42, 44, 78, 52, 63, 67],
          },
          {
            name: "Earnings",
            type: "bar",
            data: [
              89.25, 98.58, 68.74, 108.87, 77.54, 84.03, 51.24, 28.57, 92.57,
              42.36, 88.51, 36.57,
            ],
          },
          {
            name: "Refunds",
            type: "line",
            data: [8, 12, 7, 17, 21, 11, 5, 9, 7, 29, 12, 35],
          },
        ],
        chart: { height: 370, type: "line", toolbar: { show: !1 } },
        stroke: { curve: "straight", dashArray: [0, 0, 8], width: [2, 0, 2.2] },
        fill: { opacity: [0.1, 0.9, 1] },
        markers: { size: [0, 0, 0], strokeWidth: 2, hover: { size: 4 } },
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
            "Dec",
          ],
          axisTicks: { show: !1 },
          axisBorder: { show: !1 },
        },
        grid: {
          show: !0,
          xaxis: { lines: { show: !0 } },
          yaxis: { lines: { show: !1 } },
          padding: { top: 0, right: -2, bottom: 15, left: 10 },
        },
        legend: {
          show: !0,
          horizontalAlign: "center",
          offsetX: 0,
          offsetY: -5,
          markers: { width: 9, height: 9, radius: 6 },
          itemMargin: { horizontal: 10, vertical: 0 },
        },
        plotOptions: { bar: { columnWidth: "30%", barHeight: "70%" } },
        colors: linechartcustomerColors,
        tooltip: {
          shared: !0,
          y: [
            {
              formatter: function (e) {
                return void 0 !== e ? e.toFixed(0) : e;
              },
            },
            {
              formatter: function (e) {
                return void 0 !== e ? "$" + e.toFixed(2) + "k" : e;
              },
            },
            {
              formatter: function (e) {
                return void 0 !== e ? e.toFixed(0) + " Sales" : e;
              },
            },
          ],
        },
      }),
      (chart = new ApexCharts(
        document.querySelector("#customer_impression_charts"),
        options
      )).render()),
    document.querySelector(".counter-value")),
  VerticalSwiper =
    (counterValue &&
      ((counter = document.querySelectorAll(".counter-value")),
      (speed = 250),
      counter) &&
      Array.from(counter).forEach(function (a) {
        !(function e() {
          var t = +a.getAttribute("data-target"),
            r = +a.innerText,
            i = t / speed;
          i < 1 && (i = 1),
            r < t
              ? ((a.innerText = (r + i).toFixed(0)), setTimeout(e, 1))
              : (a.innerText = t);
        })();
      }),
    document.querySelector(".vertical-swiper"));
VerticalSwiper &&
  (swiper = new Swiper(".vertical-swiper", {
    slidesPerView: 2,
    spaceBetween: 10,
    mousewheel: !0,
    loop: !0,
    direction: "vertical",
    autoplay: { delay: 2500, disableOnInteraction: !1 },
  }));
