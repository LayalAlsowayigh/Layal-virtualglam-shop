const API='http://localhost:8080/api';
async function loadProducts(){
  const res=await fetch(`${API}/products`); const products=await res.json();
  const catalog=document.getElementById('catalog'); catalog.innerHTML='';
  products.forEach(p=>{
    const div=document.createElement('div'); div.className='card';
    div.innerHTML=`<img src="${p.imageURL||'images/placeholder.jpg'}" alt="${p.name}">
      <h3>${p.name}</h3><div>${p.brand} • ${p.category}</div>
      <div class="price">$${Number(p.price).toFixed(2)}</div>
      <button>Add to Cart</button>`;
    div.querySelector('button').addEventListener('click',()=>addToCart(p)); catalog.appendChild(div);
  });
}
function getCart(){return JSON.parse(localStorage.getItem('cart')||'[]')}
function saveCart(c){localStorage.setItem('cart',JSON.stringify(c));renderCart()}
function addToCart(p){const c=getCart();const f=c.find(i=>i.id===p.id);
  if(f)f.qty+=1;else c.push({id:p.id,name:p.name,price:Number(p.price),qty:1}); saveCart(c);}
function removeFromCart(id){saveCart(getCart().filter(i=>i.id!==id))}
function renderCart(){const ul=document.getElementById('cart-items'); const totalEl=document.getElementById('cart-total');
  ul.innerHTML=''; let total=0; getCart().forEach(i=>{total+=i.price*i.qty;
    const li=document.createElement('li'); li.innerHTML=`<span>${i.name} x${i.qty}</span>
    <span>$${(i.price*i.qty).toFixed(2)} <a href="#" data-id="${i.id}">✕</a></span>`;
    li.querySelector('a').addEventListener('click',e=>{e.preventDefault();removeFromCart(i.id)}); ul.appendChild(li);});
  totalEl.textContent=`Total: $${total.toFixed(2)}`;}
document.addEventListener('DOMContentLoaded',()=>{loadProducts();renderCart()});
