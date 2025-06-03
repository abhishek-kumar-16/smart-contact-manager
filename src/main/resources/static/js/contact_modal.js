

const baseUrl = "localhost:8081" // Get the base URL of the application

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
   await fetch(`http://${baseUrl}/api/contact/${id}`) // back tick helps to use dynamic URL
        .then(response => response.json())
        .then(data => {
            console.log(data);
            document.getElementById('contact_name').innerText = data.name;
            document.getElementById('contact_email').innerText = data.email;
            document.getElementById('contact_phone').innerText = data.phone;
            document.getElementById('contact_address').innerText = data.address;
            document.getElementById('contact_description').innerText = data.description;
            document.getElementById('contact_socialMedia').innerText = data.socialMedia;
            document.getElementById('contact_profilePic').src = data.profilePic || 'https://imgs.search.brave.com/Rsqj-i9kTqj1xn9KchyzS5aAGdNqsZACJ2x8gn91vTM/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly90aHVt/YnMuZHJlYW1zdGlt/ZS5jb20vYi9wcm9m/aWxlLXBsYWNlaG9s/ZGVyLWltYWdlLWdy/YXktc2lsaG91ZXR0/ZS1uby1waG90by1w/cm9maWxlLXBsYWNl/aG9sZGVyLWltYWdl/LWdyYXktc2lsaG91/ZXR0ZS1uby1waG90/by1wZXJzb24tYXZh/dGFyLTEyMzQ3ODQz/OC5qcGc';
            
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

// delete contact
 async function deleteContact(id) { 
    Swal.fire({
    icon: "warning",
  title: "Do you want to delete the contact?",
  showDenyButton: false,
  showCancelButton: true,
  confirmButtonColor: "#d33",
  confirmButtonText: "Delete",
   customClass: {
    popup:
      'bg-white text-gray-900 dark:bg-gray-800 dark:text-gray-100 rounded-lg shadow-xl',
    title: 'text-xl font-semibold',
    htmlContainer: 'text-sm',
    cancelButton:
      'bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 dark:bg-blue-500 dark:hover:bg-blue-600',
    confirmButton:
      'bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700 dark:bg-red-500 dark:hover:bg-red-600'
  }
//   denyButtonText: `Cancel`
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed) {
    window.location.replace(`http://${baseUrl}/user/contact/delete/${id}`);
    // or you can use fetch to delete the contact
    Swal.fire("Deleted!", "", "success");
  } else if (result.isDenied) {
    Swal.fire("Contact not deleted", "", "info");
  }
});
 }
