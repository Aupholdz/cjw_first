
ajax = (url, data) => $.ajax({ url, data })

$(() => {
    ajax("http://localhost:8080/SunshineAirlines/getCityNames").then(result => {
        result = JSON.parse(result)

        let html = ""
        result.data.forEach(item => html += `<option value="${item.CityName}">${item.CityName}</option>`)
        $(".fromCity").html(html)
        $(".toCity").html(html)
    })

    $(".changeicon").click(() => {
        const fromCity = $(".fromCity").val()
        const toCity = $(".toCity").val()
        $(".fromCity").val(toCity)
        $(".toCity").val(fromCity)
    })

    $(".searchBtn").click(getSchedule)
})

function updateSchedule(scheduleId, status) {
    ajax("http://localhost:8080/SunshineAirlines/updateSchedule", { scheduleId, status }).then(result => {
        result = JSON.parse(result)
        if (result.flag === "fail") return
        getSchedule()
    })
}

function getSchedule() {
    ajax("http://localhost:8080/SunshineAirlines/getSchedule", {
        fromCity: $(".fromCity").val(),
        toCity: $(".toCity").val(),
        startDate: $(".startDate").val(),
        endDate: $(".endDate").val(),
    }).then(result => {
        result = JSON.parse(result)

        let html = ""
        result.data.forEach((item, index) => {
            const color = index % 2 ? "tdcolor1" : "tdcolor"
            const statusBtn = item.Status === "Confirmed" ? "Cancel" : "Confirm"
            html += `<tr class="${color}">
                        <td>${item.Date.substring(0, 10)}</td>
                        <td>${item.Time.substring(0, 5)}</td>
                        <td>${item.DepartCityName}/${item.DepartureAirportIATA}</td>
                        <td>${item.ArriveCityName}/${item.ArrivalAirportIATA}</td>
                        <td>${item.Name}</td>
                        <td>${item.EconomyPrice}</td>
                        <td>${item.FlightNumber}</td>
                        <td>${item.Gate}</td>
                        <td>${item.Status}</td>
                        <td><input type='button' onclick="updateSchedule(${item.ScheduleId}, '${statusBtn}ed')" value='${statusBtn}' /></td>
                    </tr>`
        })
        $(".resultList").html(html)
    })
}

