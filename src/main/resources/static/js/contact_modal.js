

console.log('contact_modal.js loaded');

const viewContactModal = document.getElementById('contact_modal_view');

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'contact_modal_view',
  override: true
};



/*
 * $targetEl: required
 * options: optional
 */
const contact_modal = new Modal(viewContactModal, options, instanceOptions);

function showContactModal() {
    // Show the modal
    contact_modal.show();
}

function hideContactModal() {
    // Hide the modal
    contact_modal.hide();
}

async function loadContactData(id) {
   await fetch(`http://localhost:8081/api/contact/${id}`) // back tick helps to use dynamic URL
        .then(response => response.json())
        .then(data => {
            console.log(data);
            document.getElementById('contact_name').innerText = data.name;
            document.getElementById('contact_email').innerText = data.email;
            document.getElementById('contact_phone').innerText = data.phone;
            document.getElementById('contact_address').innerText = data.address;
            document.getElementById('contact_description').innerText = data.description;
            if(data.favorite) {
               document.getElementById("favorite-icon").style.display = "inline"; // show
            }
            else {
                document.getElementById("favorite-icon").style.display = "none"; // show
            }
             showContactModal();
        })
        .catch(error => {
            console.error('Error fetching contact data:', error);
        });
}
