function getTime(e) {
	if (null != (e = new Date(e)).getHours())
		return e.getHours() + ":" + (e.getMinutes() ? e.getMinutes() : 0);
}
function tConvert(e) {
	var e = new Date(e),
		e = (time_s = e.getHours() + ":" + e.getMinutes()).split(":"),
		t = e[0],
		e = e[1],
		a = 12 <= t ? "PM" : "AM";
	return (t = (t %= 12) || 12) + ":" + (e < 10 ? "0" + e : e) + " " + a;
}
var invoice_new_obj,
	str_dt = function(e) {
		var e = new Date(e),
			t =
				"" +
				[
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
				][e.getMonth()],
			a = "" + e.getDate(),
			e = e.getFullYear();
		return (
			t.length < 2 && (t = "0" + t),
			[(a = a.length < 2 ? "0" + a : a) + " " + t, e].join(", ")
		);
	},
	date = new Date(),
	d = date.getDate(),
	m = date.getMonth(),
	y = date.getFullYear(),
	qty = 0,
	rate = 0,
	Invoices = [
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "Checked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "UnChecked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "UnChecked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "Checked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "UnChecked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "UnChecked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "Checked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "UnChecked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "UnChecked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "Checked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "UnChecked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "UnChecked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "Checked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "UnChecked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
		{
			invoice_no: "123456789",
			customer: "고객명",
			img: "",
			email: "contact@contact.com",
			date: new Date(2024, 1, d - 23, 21, 58),
			status: "UnChecked",
			billing_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			shipping_address: {
				full_name: "이름",
				address: "주소",
				phone: "연락처",
			},
			prducts: [
				{
					product_name: "제품명",
					product_details: "제품 분류",
					rates: (rate = 119.99),
					quantity: (qty = 2),
					amount: rate * qty,
				},
			],
			payment_details: {
				payment_method: "VISA",
				card_holder_name: "카드소유자명",
				card_number: "4024007183253102",
				total_amount: 415.96,
			},
			company_details: {
				legal_registration_no: "등록번호",
				email: "이메일",
				contact_no: "0123456789",
				address: "주소",
				zip_code: "우편번호",
			},
			order_summary: {
				sub_total: 359.96,
				estimated_tex: 44.99,
				discount: 53.99,
				shipping_charge: 65,
				total_amount: 415.96,
			},
		},
	],
	checkAll =
		((null === localStorage.getItem("invoices-list") &&
			null === localStorage.getItem("new_data_object")) ||
			(null === localStorage.getItem("invoices-list") &&
				null !== localStorage.getItem("new_data_object")
				? ((invoice_new_obj = JSON.parse(
					localStorage.getItem("new_data_object")
				)),
					Invoices.push(invoice_new_obj),
					localStorage.removeItem("new_data_object"))
				: ((Invoices = []),
					(Invoices = JSON.parse(localStorage.getItem("invoices-list"))),
					null !== localStorage.getItem("new_data_object") &&
					((invoice_new_obj = JSON.parse(
						localStorage.getItem("new_data_object")
					)),
						Invoices.push(invoice_new_obj),
						localStorage.removeItem("new_data_object")),
					localStorage.removeItem("invoices-list"))),
			Array.from(Invoices).forEach(function(e) {
				let t;
				switch (e.status) {
					case "Checked":
						t = "primary";
						break;
					case "UnChecked":
						t = "warning";
						break;
					case "Cancel":
						t = "danger";
				}
				a = e.img
					? "<img src='/administration/assets/images/users/" +
					e.img +
					"' alt='' class='avatar-xs rounded-circle me-2'>"
					: '<div class="flex-shrink-0 avatar-xs me-2"><div class="avatar-title bg-success-subtle text-success rounded-circle fs-13">' +
					(2 <= (a = e.customer.split(" ")).length
						? a[0].slice(0, 1) + a[1].slice(0, 1)
						: a[0].slice(0, 1)) +
					"</div></div>";
				var a =
					`<tr>
                <td class="id"><a href="TEL : 010-1234-1234">010-1234-1234</a></td>
                <td class="customer_name">
                    <div class="d-flex align-items-center">
                        ` +
					a +
					e.customer +
					`
                    </div>
                </td>
                <td class="email">` +
					e.email +
					`</td>
                <td class="date">` +
					str_dt(e.date) +
					' <small class="text-muted">' +
					tConvert(e.date) +
					`</small></td>
                <td class="status"><span class="badge bg-` +
					t +
					"-subtle text-" +
					t +
					' text-uppercase">' +
					e.status +
					`</span>
                </td>
                <td>
                    <div class="dropdown">
                        <button class="btn btn-soft-secondary btn-sm dropdown" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="ri-more-fill align-middle"></i>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item remove-item-btn" data-bs-toggle="modal" href="#changeOrder">
                                   	<i class="bx bx-refresh"></i>
                                    전환하기
                                </a>
                            </li>
                            <li class="dropdown-divider"></li>
                            <li>
                                <a class="dropdown-item remove-item-btn" data-bs-toggle="modal" href="#deleteOrder">
                                    <i class="ri-delete-bin-fill align-bottom me-2 text-muted"></i>
                                    Delete
                                </a>
                            </li>
                        </ul>
                    </div>
                </td>
            </tr>`;
				document.getElementById("invoice-list-data").innerHTML += a;
			}),
			document.addEventListener("DOMContentLoaded", function() {
				var e = document.querySelectorAll('[data-plugin="choices"]');
				Array.from(e).forEach(function(e) {
					new Choices(e, {
						placeholderValue: "This is a placeholder set in the config",
						searchPlaceholderValue: "Search results here",
					});
				});
			}),
			flatpickr("#datepicker-range", { mode: "range", dateFormat: "d M, Y" }),
			flatpickr("#date-field", { dateFormat: "d M, Y" }),
			document.getElementById("checkAll")),
	perPage =
		(checkAll &&
			(checkAll.onclick = function() {
				for (
					var e = document.querySelectorAll(
						'.form-check-all input[type="checkbox"]'
					),
					t = document.querySelectorAll(
						'.form-check-all input[type="checkbox"]:checked'
					).length,
					a = 0;
					a < e.length;
					a++
				)
					(e[a].checked = this.checked),
						e[a].checked
							? e[a].closest("tr").classList.add("table-active")
							: e[a].closest("tr").classList.remove("table-active");
				document.getElementById("remove-actions").style.display =
					0 < t ? "none" : "block";
			}),
			8),
	options = {
		valueNames: [
			"Phone",
			"customer_name",
			"email",
			"date",
			"status",
		],
		page: perPage,
		pagination: !0,
		plugins: [ListPagination({ left: 2, right: 2 })],
	},
	invoiceList = new List("invoiceList", options).on("updated", function(e) {
		0 == e.matchingItems.length
			? (document.getElementsByClassName("noresult")[0].style.display = "block")
			: (document.getElementsByClassName("noresult")[0].style.display = "none");
		var t = 1 == e.i,
			a = e.i > e.matchingItems.length - e.page;
		document.querySelector(".pagination-prev.disabled") &&
			document
				.querySelector(".pagination-prev.disabled")
				.classList.remove("disabled"),
			document.querySelector(".pagination-next.disabled") &&
			document
				.querySelector(".pagination-next.disabled")
				.classList.remove("disabled"),
			t && document.querySelector(".pagination-prev").classList.add("disabled"),
			a && document.querySelector(".pagination-next").classList.add("disabled"),
			e.matchingItems.length <= perPage
				? (document.querySelector(".pagination-wrap").style.display = "none")
				: (document.querySelector(".pagination-wrap").style.display = "flex"),
			e.matchingItems.length == perPage &&
			document
				.querySelector(".pagination.listjs-pagination")
				.firstElementChild.children[0].click(),
			0 < e.matchingItems.length
				? (document.getElementsByClassName("noresult")[0].style.display =
					"none")
				: (document.getElementsByClassName("noresult")[0].style.display =
					"block");
	}),
	isValue = (isCount = new DOMParser().parseFromString(
		invoiceList.items.slice(-1)[0]._values.id,
		"text/html"
	)).body.firstElementChild.innerHTML,
	idField = document.getElementById("orderId"),
	customerNameField = document.getElementById("customername-field"),
	emailField = document.getElementById("email-field"),
	dateField = document.getElementById("date-field"),
	countryField = document.getElementById("country-field"),
	statusField = document.getElementById("delivered-status"),
	addBtn = document.getElementById("add-btn"),
	editBtn = document.getElementById("edit-btn"),
	removeBtns = document.getElementsByClassName("remove-item-btn"),
	editBtns = document.getElementsByClassName("edit-item-btn");
function filterContact(e) {
	var t = e;
	invoiceList.filter(function(e) {
		e = (matchData = new DOMParser().parseFromString(
			e.values().status,
			"text/html"
		)).body.firstElementChild.innerHTML;
		return "All" == e || "All" == t || e == t;
	}),
		invoiceList.update();
}
function updateList() {
	var t = document.querySelector("input[name=status]:checked").value;
	(data = userList.filter(function(e) {
		return "All" == t || e.values().sts == t;
	})),
		userList.update();
}
refreshCallbacks(), filterContact("All");
var table = document.getElementById("invoiceTable"),
	tr = table.getElementsByTagName("tr"),
	trlist = table.querySelectorAll(".list tr");
function SearchData() {
	var i = document.getElementById("idStatus").value,
		o = document.getElementById("datepicker-range").value,
		r = o.split(" to ")[0],
		s = o.split(" to ")[1];
	invoiceList.filter(function(e) {
		var t = (matchData = new DOMParser().parseFromString(
			e.values().status,
			"text/html"
		)).body.firstElementChild.innerHTML,
			a = !1,
			n = !1,
			a = "all" == t || "all" == i || t == i,
			n =
				new Date(e.values().date.slice(0, 12)) >= new Date(r) &&
				new Date(e.values().date.slice(0, 12)) <= new Date(s);
		return (a && n) || (a && "" == o ? a : n && "" == o ? n : void 0);
	}),
		invoiceList.update();
}
function ischeckboxcheck() {
	Array.from(document.getElementsByName("chk_child")).forEach(function(a) {
		a.addEventListener("change", function(e) {
			1 == a.checked
				? e.target.closest("tr").classList.add("table-active")
				: e.target.closest("tr").classList.remove("table-active");
			var t = document.querySelectorAll('[name="chk_child"]:checked').length;
			e.target.closest("tr").classList.contains("table-active"),
				(document.getElementById("remove-actions").style.display =
					0 < t ? "block" : "none");
		});
	});
}
function refreshCallbacks() {
	Array.from(removeBtns).forEach(function(e) {
		e.addEventListener("click", function(e) {
			e.target.closest("tr").children[1].innerText,
				(itemId = e.target.closest("tr").children[1].innerText);
			e = invoiceList.get({ id: itemId });
			Array.from(e).forEach(function(e) {
				var t = (deleteid = new DOMParser().parseFromString(
					e._values.id,
					"text/html"
				)).body.firstElementChild;
				deleteid.body.firstElementChild.innerHTML == itemId &&
					document
						.getElementById("delete-record")
						.addEventListener("click", function() {
							invoiceList.remove("id", t.outerHTML),
								document.getElementById("deleteRecord-close").click();
						});
			});
		});
	});
}
function clearFields() {
	(customerNameField.value = ""),
		(emailField.value = ""),
		(dateField.value = ""),
		(countryField.value = "");
}
function ViewInvoice(e) {
	e = e.getAttribute("data-id");
	localStorage.setItem("invoices-list", JSON.stringify(Invoices)),
		localStorage.setItem("option", "view-invoice"),
		localStorage.setItem("invoice_no", e),
		window.location.assign("/admin/memberBillManagerDetail");
}
function EditInvoice(e) {
	e = e.getAttribute("data-id");
	localStorage.setItem("invoices-list", JSON.stringify(Invoices)),
		localStorage.setItem("option", "edit-invoice"),
		localStorage.setItem("invoice_no", e),
		window.location.assign("apps-invoices-create.html");
}
function deleteMultiple() {
	ids_array = [];
	var e = document.getElementsByName("chk_child");
	for (i = 0; i < e.length; i++)
		1 == e[i].checked && ids_array.push(e[i].value);
	"undefined" != typeof ids_array && 0 < ids_array.length
		? Swal.fire({
			title: "Are you sure?",
			text: "You won't be able to revert this!",
			icon: "warning",
			showCancelButton: !0,
			confirmButtonClass: "btn btn-primary w-xs me-2 mt-2",
			cancelButtonClass: "btn btn-danger w-xs mt-2",
			confirmButtonText: "Yes, delete it!",
			buttonsStyling: !1,
			showCloseButton: !0,
		}).then(function(e) {
			if (e.value) {
				for (i = 0; i < ids_array.length; i++)
					invoiceList.remove(
						"id",
						'<a href="javascript:void(0);" onclick="ViewInvoice(this);" data-id="' +
						ids_array[i].slice(3) +
						`" class="fw-medium link-primary">${ids_array[i]}</a>`
					);
				(document.getElementById("remove-actions").style.display = "none"),
					(document.getElementById("checkAll").checked = !1),
					Swal.fire({
						title: "Deleted!",
						text: "Your data has been deleted.",
						icon: "success",
						confirmButtonClass: "btn btn-info w-xs mt-2",
						buttonsStyling: !1,
					});
			}
		})
		: Swal.fire({
			title: "Please select at least one checkbox",
			confirmButtonClass: "btn btn-info",
			buttonsStyling: !1,
			showCloseButton: !0,
		});
}
document.querySelector("#invoiceList").addEventListener("click", function() {
	ischeckboxcheck();
}),
	document
		.querySelector(".pagination-next")
		.addEventListener("click", function() {
			document.querySelector(".pagination.listjs-pagination") &&
				document
					.querySelector(".pagination.listjs-pagination")
					.querySelector(".active") &&
				document
					.querySelector(".pagination.listjs-pagination")
					.querySelector(".active")
					.nextElementSibling.children[0].click();
		}),
	document
		.querySelector(".pagination-prev")
		.addEventListener("click", function() {
			document.querySelector(".pagination.listjs-pagination") &&
				document
					.querySelector(".pagination.listjs-pagination")
					.querySelector(".active") &&
				document
					.querySelector(".pagination.listjs-pagination")
					.querySelector(".active")
					.previousSibling.children[0].click();
		});
