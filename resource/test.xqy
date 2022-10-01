declare variable $param1 as xs:string external;
for $x in doc($param1)/bookstore/book
where $x/price>40
order by $x/title
return $x/title