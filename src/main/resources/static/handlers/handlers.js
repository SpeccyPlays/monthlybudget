function handleYearClick(event, year) {
    event.preventDefault(); // Stops the default link behavior
    const yearDiv = `divyear_${year}`;
    const allYearDivs = document.querySelectorAll(".divyearcontainer");
    console.log("Year div: ", yearDiv);
    console.log(allYearDivs);
    allYearDivs.forEach(div =>{
        console.log(div);
        const displayValue = div.id === yearDiv ? "block" : "none"
        div.style.display = displayValue;
    });
}
function handleMonthClick(event, month) {
    event.preventDefault(); // Stops the default link behavior
    const monthTbl = `tbl_${month}`;
    const allMonthtables = document.querySelectorAll("table");
    allMonthtables.forEach(table =>{
        const displayValue = table.id === monthTbl ? "block" : "none"
        table.style.display = displayValue; 
    });
}