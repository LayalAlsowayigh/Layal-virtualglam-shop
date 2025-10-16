// ===== Utilities =====
const $ = sel => document.querySelector(sel);
const grid = $('#grid'), search = $('#search'), category = $('#category');
const cartBtn = $('#cartBtn'), cartDrawer = $('#cartDrawer'), closeCart = $('#closeCart');
const cartItems = $('#cartItems'), cartTotal = $('#cartTotal'), cartCount = $('#cartCount');
const checkoutBtn = $('#checkoutBtn'), toast = $('#toast');
$('#year').textContent = new Date().getFullYear();

function money(n){ return `$${n.toFixed(2)}`; }

// ===== Products (your exact list) =====
// Put product photos in /images using these filenames (or change the "image" values)
const PRODUCTS = [
  // skincare
  {category:"Skincare", type:"Moisturizer", name:"Charlotte’s Magic Cream", shade:"none", price:66.0, brand:"Charlotte Tilbury", image:"magic-cream.jpg"},
  {category:"Skincare", type:"Primer", name:"REFY Face Primer", shade:"clear", price:28.0, brand:"REFY", image:"refy-primer.jpg"},

  // foundation
  {category:"Foundation", type:"Foundation", name:"Triclone Skin Tech Foundation", shade:"Light 160 Cool", price:49.0, brand:"Haus Labs", image:"triclone-160c.jpg"},
  {category:"Foundation", type:"Foundation", name:"Triclone Skin Tech Foundation", shade:"Medium 250 Neutral", price:49.0, brand:"Haus Labs", image:"triclone-250n.jpg"},
  {category:"Foundation", type:"Foundation", name:"Triclone Skin Tech Foundation", shade:"Deep 460 Warm", price:49.0, brand:"Haus Labs", image:"triclone-460w.jpg"},

  // powders
  {category:"Powders", type:"Setting Powder", name:"Easy Bake Loose Powder - Pound Cake", shade:"light neutral", price:38.0, brand:"Huda Beauty", image:"pound-cake.jpg"},
  {category:"Powders", type:"Setting Powder", name:"Easy Bake Loose Powder - Cherry Blossom", shade:"bright pink", price:38.0, brand:"Huda Beauty", image:"cherry-blossom.jpg"},
  {category:"Powders", type:"Setting Powder", name:"Easy Bake Loose Powder - Cinnamon Bun", shade:"tan neutral", price:38.0, brand:"Huda Beauty", image:"cinnamon-bun.jpg"},

  // sculpt
  {category:"Sculpt", type:"Bronzer Stick", name:"SoftSculpt Shaping Stick", shade:"Light", price:32.0, brand:"Makeup by Mario", image:"mario-stick-light.jpg"},
  {category:"Sculpt", type:"Bronzer", name:"Nu Bronzer (Le Nu Glow Powder Bronzer)", shade:"warm bronze", price:55.0, brand:"YSL", image:"ysl-nu-bronzer.jpg"},

  // color
  {category:"Color", type:"Blush Stick", name:"Soft Pop Blush Stick", shade:"Peachy", price:30.0, brand:"Makeup by Mario", image:"mario-blush-peachy.jpg"},
  {category:"Color", type:"Blush Duo", name:"Major Headlines Double-Take Crème & Powder Blush Duo", shade:"She's Vibrant (rose)", price:38.0, brand:"Patrick Ta", image:"patrickta-duo.jpg"},
  {category:"Color", type:"Blush", name:"Nu Blush (Le Nu Glow Powder Blush)", shade:"rosy nude", price:45.0, brand:"YSL", image:"ysl-nu-blush.jpg"},

  // highlight
  {category:"Highlight", type:"Highlighter Palette", name:"Backstage Glow Face Palette - 001 Universal", shade:"universal", price:48.0, brand:"Dior", image:"dior-glow-001.jpg"},

  // setting spray
  {category:"Setting Spray", type:"Setting Spray", name:"Airbrush Flawless Setting Spray", shade:"clear", price:38.0, brand:"Charlotte Tilbury", image:"ct-setting-spray.jpg"},
];

// ===== Render products =====
function renderProducts() {
  const q = search.value.trim().toLowerCase();
  const cat = category.value;
  const list = PRODUCTS
    .filter(p => cat==='all' || p.category===cat)
    .filter(p => `${p.type} ${p.name} ${p.shade} ${p.brand}`.toLowerCase().includes(q));

  grid.innerHTML = list.map((p, idx) => `
    <div class="card" tabindex="0">
      <img src="/images/${p.image}" alt="${p.name}"/>
      <div class="content">
        <div class="badge">${p.category} · ${p.type}</div>
        <div class="title">${p.name}</div>
        <div class="meta">${p.brand}${p.shade ? ' · '+p.shade : ''}</div>
        <div class="price">${money(p.price)}</div>
        <button data-idx="${idx}">Add to Cart</button>
      </div>
    </div>
  `).join('');

  grid.querySelectorAll('button').forEach(btn => {
    btn.addEventListener('click', e => {
      const p = list[Number(e.currentTarget.dataset.idx)];
      addToCart(p);
    });
  });
}

// ===== Cart logic (client-side demo) =====
let cart = [];

function addToCart(p){
  const f = cart.find(i => i.name===p.name && i.shade===p.shade);
  if(f) f.qty += 1; else cart.push({...p, qty:1});
  saveCart(); showToast(`${p.name} added`);
}
function inc(i){ cart[i].qty++; saveCart(); }
function dec(i){ cart[i].qty--; if(cart[i].qty<=0) cart.splice(i,1); saveCart(); }
function removeItem(i){ cart.splice(i,1); saveCart(); }

function saveCart(){
  renderCart();
  cartCount.textContent = cart.reduce((a,i)=>a+i.qty,0);
}
function renderCart(){
  cartItems.innerHTML = cart.map((i,idx)=>`
    <li class="cart-item">
      <div>
        <div><strong>${i.name}</strong></div>
        <div class="meta">${i.brand} · ${i.shade || ''}</div>
        <div>${money(i.price)}</div>
      </div>
      <div class="qty">
        <button aria-label="decrease" onclick="dec(${idx})">-</button>
        <span>${i.qty}</span>
        <button aria-label="increase" onclick="inc(${idx})">+</button>
        <button aria-label="remove" onclick="removeItem(${idx})" title="remove">✕</button>
      </div>
    </li>
  `).join('');
  const total = cart.reduce((a,i)=>a+i.price*i.qty,0);
  cartTotal.textContent = `Total: ${money(total)}`;
}

function showToast(msg){
  toast.textContent = msg; toast.classList.remove('hidden');
  setTimeout(()=>toast.classList.add('hidden'), 900);
}

// ===== Events =====
cartBtn.addEventListener('click', ()=>{
  cartDrawer.classList.toggle('hidden');
  cartDrawer.setAttribute('aria-hidden', cartDrawer.classList.contains('hidden'));
});
closeCart.addEventListener('click', ()=>{
  cartDrawer.classList.add('hidden');
  cartDrawer.setAttribute('aria-hidden', 'true');
});
search.addEventListener('input', renderProducts);
category.addEventListener('change', renderProducts);
checkoutBtn.addEventListener('click', ()=>{
  if(cart.length===0){ showToast("Cart is empty"); return; }
  showToast("Payment simulated ✔");
  cart = []; saveCart();
});

// Start
renderProducts(); saveCart();

// expose for inline onclicks
window.inc = inc; window.dec = dec; window.removeItem = removeItem;
