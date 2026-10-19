# Microsoft Store listing — Insait Invoice Calc JE

> Store listing copy for **Insait Invoice Calc JE** (Java Edition).
> Ready to paste into the Microsoft Partner Center fields.
> Character counts are checked against the Partner Center limits and noted per field.

---

## 1. Product identity

| Field | Value | Limit |
| --- | --- | --- |
| Product name | `Insait Invoice Calc JE` | 22 / 50 characters |
| Store listing name (short form) | `Insait Invoice Calc JE` | 22 / 30 characters |
| Package identity name (MSIX) | `InsaitInvoiceCalcJE` | reserved name, must match `Publisher.DisplayName` |
| Publisher display name | `<your publisher name as registered in Partner Center>` | — |
| Version | `1.0.0` | — |
| Default language | English (United States) | — |

`JE` stands for **Java Edition**. The suffix keeps the name unique in the Store and makes the JavaFX origin visible to buyers.

---

## 2. Short description

**Limit: 300 characters. Current: 175 characters.**

> A fast desktop price calculator for batches. Enter unit price and quantity, add a customer discount and VAT, and read the final batch price and the price per unit at a glance.

---

## 3. Full description

**Limit: 4000 characters. Current: 2144 characters.**

> Insait Invoice Calc JE is a lightweight desktop calculator for anyone who quotes prices for a batch: tradespeople, retailers, warehouses, consultants and office teams.
>
> Enter a unit price and a quantity, optionally add a customer discount in percent and VAT in percent, and the app instantly shows you the final price of the whole batch and the effective price per unit. No account, no internet connection, no subscription.
>
> ### What it calculates
>
> - Net subtotal of a batch: unit price multiplied by quantity
> - Customer discount in percent, applied to the whole batch
> - VAT in percent, applied to the amount after the discount
> - Final price of the batch
> - Effective price per unit after discount and VAT
> - A clear invoice summary with subtotal, discount amount, tax amount, batch total and unit price
>
> ### Built for real working days
>
> - Works fully offline. Your prices and quantities never leave your computer.
> - Both decimal separators are accepted: type `49,99` or `49.99`, it does not matter.
> - Amounts are rounded to cents, formatted as thousands separators, for example `4,759.05 EUR`.
> - The quantity can be typed directly or adjusted with the minus and plus buttons.
> - The calculation runs when you press Calculate or Enter, so a half-typed value is never used by mistake.
> - Invalid input is reported in plain language instead of producing a wrong sum.
> - The layout scales down cleanly on small laptop screens and can be resized freely.
>
> ### Example
>
> Unit price 49,99 EUR, quantity 100, discount 20 percent, VAT 19 percent:
> subtotal 4,999.00 EUR, discount 999.80 EUR, VAT 759.85 EUR, final batch price 4,759.05 EUR, that is 47.59 EUR per unit.
>
> ### Good to know
>
> - Runs on Windows 10 and Windows 11, 64-bit.
> - Results are shown in EUR as the main display currency.
> - The app already contains its own Java runtime. Nothing has to be installed on your computer.
> - The user interface is English.
> - The terms of use are available directly in the title bar of the app.
> - Purchase, updates and refunds are handled by the Microsoft Store.
>
> Insait Invoice Calc JE does exactly one thing and does it without ads, tracking or accounts.

---

## 4. Store listing metadata

| Field | Value |
| --- | --- |
| Category | Business > Accounting & Finance |
| Secondary category | Productivity |
| Content type | App |
| Age rating | 3+ |
| Price | Free, or your chosen paid price |
| Contains ads | No |
| In-app purchases | No |
| Languages of the app itself | English (United States) |
| Supported OS | Windows 10 (build 17763) or later, Windows 11, x64 |
| Developer name | `<your developer / publisher name>` |
| Contact e-mail | `<support e-mail shown in the listing>` |

### Search keywords

Use these in the Partner Center keyword field. Keep them separated by commas, no repetition of words already in the title.

```
price calculator, batch price, discount, VAT, net subtotal, wholesale, unit price,
invoice, quotation, pricing, trade, retail, calculator, percentage calculator,
tax, goods price, sales calculation, offline calculator, business calculator
```

---

## 5. Store listing assets

| Asset | Requirement | Source in this repository |
| --- | --- | --- |
| App icon | 300 x 300 px, `.png` | `src/main/resources/image/appicon.png` (768 x 768, downscale to 300 x 300) |
| Store icon, small | 50 x 50 px | same source |
| Screenshots | 1 to 5, 1920 x 1080 px, `.jpg` or `.png` | capture the running app |
| Wide tile (optional) | 1000 x 250 px | — |
| Promotional image (optional) | 1920 x 1080 px | — |

Suggested screenshot set, all at 1920 x 1080:

1. Main window with the input card, the big total and the invoice summary filled in with the example from the description.
2. Close-up of the invoice summary showing subtotal, discount, VAT, batch total and unit price.
3. The terms of use window, showing that the licence is built into the app.
4. A second example with a different quantity and no discount, to show the two use cases.

Make sure no customer data, real invoices or personal names appear in the screenshots.

---

## 6. Privacy

The app has no network code, no analytics and no accounts. Declare the following in the Partner Center privacy declaration:

| Question | Answer |
| --- | --- |
| Does the app collect data? | No |
| Data is encrypted in transit | Not applicable, no data is transmitted |
| User can request data deletion | Not applicable, no data is stored |
| Data is shared with third parties | No |

A privacy policy URL is still required. Put it on the same web page as your support contact, for example `https://<your-domain>/privacy`, and state: offline app, no data collection, no tracking, no cookies. The same URL is used as the support URL if you keep both on one page.

---

## 7. Submission checklist

Technical items to confirm before you submit:

- [ ] The submission package must be **MSIX (`.msix` / `.msixbundle`)**, not the loose `jpackage` app-image. A `.msix` build is required for the Store, the `app-image` folder in `target/selfcontained/app/` is for direct distribution only.
- [ ] Reserved package identity name `InsaitInvoiceCalcJE` matches the identity in the signed MSIX manifest.
- [ ] MSIX is signed with a certificate trusted by Windows, upload the `.cer` to Partner Center.
- [ ] Minimum supported Windows version declared in the manifest matches the value in the metadata table above.
- [ ] `InsaitInvoiceCalc.exe` is declared as the app entry point, with the bundled runtime inside the package.
- [ ] Terms of use shown in the app match the licence text published on your web page.
- [ ] Store listing name, short description and full description are entered for **English (United States)** and for every market you enable.
- [ ] Screenshots and icon are free of customer data.
- [ ] Privacy declaration answered with the values from section 6.
- [ ] Age rating questionnaire completed (expect 3+, no descriptors triggered).
- [ ] Support and privacy URLs are live and return HTTP 200.

### Known point to resolve before submission

The current build pipeline in `README.md` produces an `app-image` via `jpackage` plus a ZIP. The Store does not accept that layout. You need an MSIX package, which either comes from the **Microsoft Store Developer Kit for Java (MSDKJ)** or from a `jpackage`-based MSIX pipeline. The application code itself does not have to change for this, only the packaging step.

---

## 8. Optional: Ukrainian listing

If you enable the Ukrainian market, reuse the same metadata and translate only the text fields.

**Short description, Ukrainian, 160 characters:**

> Швидкий десктопний калькулятор для партій: ціна за одиницю, кількість, знижка клієнта та ПДВ. Підсумкова ціна всієї партії та ціна за одиницею одразу на екрані.

**Full description, Ukrainian, headings only:**

> Insait Invoice Calc JE — легкий десктопний калькулятор для тих, хто виставляє рахунки за партію: майстри, продавці, склади, консультанти та офісні команди.
>
> Введіть ціну за одиницю та кількість, за потреби додайте знижку у відсотках і ПДВ у відсотках — додаток миттєво покаже підсумкову ціну всієї партії та фактичну ціну за одиницею. Без акаунта, без інтернету, без підписки.
>
> ### Що рахує
>
> - Підсумок партії: ціна за одиницю помножена на кількість
> - Знижка клієнта у відсотках на всю партію
> - ПДВ у відсотках від суми після знижки
> - Підсумкова ціна всієї партії
> - Фактична ціна за одиницею після знижки та ПДВ
> - Зведена таблиця: підсумок, сума знижки, сума податку, підсумок партії та ціна за одиницею
>
> ### Зручно в роботі
>
> - Працює повністю офлайн. Ваші ціни та кількості не залишають комп'ютер.
> - Приймаються і кома, і крапка як десятковий роздільник.
> - Суми округлюються до копійок і форматуються з роздільниками: `4 759,05 EUR`.
> - Кількість можна ввести вручну або змінити кнопками «мінус» і «плюс».
> - Розрахунок запускається кнопкою Calculate або клавішею Enter.
> - Помилковий ввід показується зрозумілим повідомленням, а не неправильною сумою.
>
> ### Приклад
>
> Ціна за одиницю 49,99 EUR, кількість 100, знижка 20 %, ПДВ 19 %: підсумок 4 999,00 EUR, знижка 999,80 EUR, ПДВ 759,85 EUR, підсумкова ціна партії 4 759,05 EUR, тобто 47,59 EUR за одиницю.
>
> ### Варто знати
>
> - Працює на Windows 10 і Windows 11, 64-розрядні версії.
> - Результати відображаються в EUR як основній валюті.
> - Java вже вбудовано в застосунок, нічого встановлювати не потрібно.
> - Інтерфейс англомовний.
> - Умови використання доступні просто в заголовку вікна.
> - Оплату, оновлення та повернення коштів обробляє Microsoft Store.
>
> Insait Invoice Calc JE робить одну справу: без реклами, відстеження та акаунтів.

Keep the terms of use, support contact and privacy policy in one place. The licence text in the app uses the German governing law clause, so the web page should match it.

---

## 9. Fields to fill in manually

| Placeholder | What to put there |
| --- | --- |
| `<your publisher name as registered in Partner Center>` | Exact legal publisher name from the Partner Center account |
| `<your developer / publisher name>` | Name shown in the store listing |
| `<support e-mail shown in the listing>` | A mailbox that is monitored |
| `https://<your-domain>/privacy` | Hosted privacy policy page |

The listing text itself needs no further editing. Only the short description, if you want it shorter for a small screen, may be trimmed to the first sentence: *A fast desktop price calculator for batches.*
