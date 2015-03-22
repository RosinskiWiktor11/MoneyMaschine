select f.id, f.shortname, v.date, v.value, a.id from investment_fund f, fund_value v, skandia_algorithm a where

f.shortname like('%HSBC 2%')

and f.id=v.investmentfund_id
and f.id=a.investmentfund_id
and a.openingalgorithm=true
order by v.date desc, a.id desc
limit 1